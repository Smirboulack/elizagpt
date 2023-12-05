package fr.univ_lyon1.info.m1.elizagpt.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.opencsv.exceptions.CsvValidationException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import fr.univ_lyon1.info.m1.elizagpt.model.Verbes.Verb;
import fr.univ_lyon1.info.m1.elizagpt.model.Verbes.VerbList;
import fr.univ_lyon1.info.m1.elizagpt.model.responserules.IResponseRule;

/**
 * Logic to process a message (and probably reply to it).
 */
public class MessageProcessor {
    // private List<HBox> messages;
    private String name;
    private final Random random = new Random();
    private VerbList verbList;

    /**
     * Constructor.
     */
    public MessageProcessor() {
        try {
            verbList = new VerbList();
        } catch (CsvValidationException e) {
            // TODO Auto-generated catch block
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
                throw 
                new FileNotFoundException("Fichier de configuration non trouvé dans le classpath");
            }

            props.load(input);
            String[] ruleClassNames = props.getProperty("rules").split(",");

            for (String className : ruleClassNames) {
                try {
                    // Ajoutez le package complet avant le nom de la classe
                    String fullClassName = "fr.univ_lyon1.info.m1.elizagpt.model.responserules."
                            + className.trim();
                    Class<?> clazz = Class.forName(fullClassName);
                    IResponseRule rule =
                     (IResponseRule) clazz.getDeclaredConstructor().newInstance();
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
     * @param text
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
     * TODO: does not deal with all 3rd group verbs.
     *
     * @param text
     * @return The 2nd-person sentence.
     * @throws CsvValidationException
     */
    public String firstToSecondPerson(final String text) throws CsvValidationException {
        System.out.println("hello ");
        String processedText = text
                .replaceAll("[Jj]e ([a-z]*)e ", "vous $1ez ");

        for (Verb verb : VerbList.getVerbs()) {
            /* System.out.print(verb.getFirstSingular());
            System.out.print(" ");
            System.out.println(verb.getSecondPlural()); */
            processedText = processedText.replaceAll(
                    "[Jj]e " + verb.getFirstSingular(),
                    "vous " + verb.getSecondPlural());

        }
        processedText = processedText
                .replaceAll("[Jj]e ([a-z]*)s ", "vous $1ssez ")
                .replace("mon ", "votre ")
                .replace("ma ", "votre ")
                .replace("mes ", "vos ")
                .replace("moi", "vous")
                .replace("m'", "vous ");

        return processedText;
    }

    /**
     * Generate a response to the input text.
     * 
     * @param input
     * @return The response (String).
     */
    public String generateResponse(final String input) {
        List<IResponseRule> responseRules = loadRulesFromConfig(
                "responseRules.properties");

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
     * @param name
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

}
