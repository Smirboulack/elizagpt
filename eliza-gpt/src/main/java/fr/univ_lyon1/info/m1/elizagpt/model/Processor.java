package fr.univ_lyon1.info.m1.elizagpt.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.opencsv.exceptions.CsvValidationException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import fr.univ_lyon1.info.m1.elizagpt.model.responserules.IResponseRule;
import fr.univ_lyon1.info.m1.elizagpt.model.verb.VerbList;

/**
 * Logic to process a message (and probably reply to it).
 */
public class Processor {
    // private List<HBox> messages;
    private String name;
    private final Random random = new Random();
    private VerbList verbList;
    private List<IResponseRule> responseRules;

    /**
     * Constructor.
     */
    public Processor() {
        try {
            verbList = new VerbList();
            responseRules = loadRulesFromConfig(
                    "responseRules.properties");
        } catch (CsvValidationException e) {
            e.printStackTrace();
        }
    }

    /**
     * Load all the response rules dynamically.
     */
    public List<IResponseRule> loadRulesFromConfig(final String configFilePath) {
        List<IResponseRule> rules = new ArrayList<>();
        Properties props = new Properties();

        // Charger le fichier properties à partir du classpath
        try (InputStream input = getClass().getClassLoader().getResourceAsStream(configFilePath)) {
            if (input == null) {
                throw new FileNotFoundException(
                        "Fichier de configuration non trouvé dans le classpath");
            }

            props.load(input);
            String[] ruleClassNames = props.getProperty("rules").split(",");

            for (String className : ruleClassNames) {
                try {
                    // Ajoutez le package complet avant le nom de la classe
                    String fullClassName = "fr.univ_lyon1.info.m1.elizagpt.model.responserules."
                            + className.trim();
                    Class<?> clazz = Class.forName(fullClassName);
                    IResponseRule rule = (IResponseRule) clazz.getDeclaredConstructor().newInstance();
                    rules.add(rule);
                } catch (ClassNotFoundException e) {
                    System.err.println("La classe " + className.trim() + " n'a pas été trouvée.");
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return rules;
    }

    /**
     * Normlize the text: remove extra spaces, add a final dot if missing.
     * 
     * @param text le texte à normalmiser
     * @return normalized text.
     */
    public String normalize(final String text) {
        return text.replaceAll("\\s+", " ")
                .replaceAll("^\\s+", "")
                .replaceAll("\\s+$", "")
                .replaceAll("[^\\.!?:]$", "$0.");
    }

    /**
     * Turn a 1st-person sentence (Je ...) into a plural 2nd person (Vous ...).
     * The result is not capitalized to allow forming a new sentence.
     *
     *
     * @param text le message à conjuger
     * @return The 2nd-person sentence.
     */
    public String firstToSecondPerson(final String text) throws CsvValidationException {
        String processedText = text;

        processedText = verbList.replacePronounsFirstToSecond(processedText);
        processedText = verbList.verbsFirstToSecond(processedText);
        processedText = verbList.replacePossessives(processedText);

        return processedText;
    }

    public String secondToFirstPerson(final String text) throws CsvValidationException {
        String processedText = text;

        processedText = verbList.replacePronounsSecondToFirst(processedText);
        processedText = verbList.verbsSecondToFirst(processedText);
        processedText = verbList.replacePossessives(processedText);

        return processedText;
    }

    /**
     * Generate a response to the input text.
     * 
     * @param input le message entré
     * @return The response (String).
     */
    public String generateResponse(final String input) {
        for (IResponseRule rule : responseRules) {
            if (rule.appliesTo(input, this)) {
                return rule.generateResponse(input, this);
            }
        }
        return null;
    }

    /** Pick an element randomly in the array. */
    public <T> T pickRandom(final T[] array) {
        return array[random.nextInt(array.length)];
    }

    /**
     * return the name.
     * 
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name.
     * 
     * @param name le nom de l'utilisateur
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Get random.
     */
    public Random getRandom() {
        return random;
    }

    public List<IResponseRule> getResponseRules() {
        return responseRules;
    }

    public void setResponseRules(final List<IResponseRule> responseRules) {
        this.responseRules = responseRules;
    }

}
