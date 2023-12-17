package fr.univ_lyon1.info.m1.elizagpt.model.verb;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

/**
 * A list of verbs.
 */
public class VerbList {

    private static final List<Verb> verbs = new ArrayList<>();

    /**
     * Constructor.
     * 
     * @throws CsvValidationException l'exception
     */
    public VerbList() throws CsvValidationException {
        loadVerbsFromCSV("src/main/resources/french-verb-conjugation.csv");
    }

    private void loadVerbsFromCSV(final String filePath) throws CsvValidationException {
        try (Reader reader = new FileReader(filePath);
                CSVReader csvReader = new CSVReader(reader)) {

            String[] nextRecord;
            while ((nextRecord = csvReader.readNext()) != null) {
                if (nextRecord.length > 9) {
                    String firstSingular = nextRecord[5];
                    String secondPlural = nextRecord[9];
                    Verb verb = new Verb(firstSingular, secondPlural);
                    verbs.add(verb);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de la lecture du fichier CSV: " + filePath, e);
        }
    }

    public static List<Verb> getVerbs() {
        return verbs;
    }

    /**
     * Normalize the given text by replacing pronouns.
     *
     * @param text The text to normalize.
     * @return The normalized text.
     */
    public String replacePronounsFirstToSecond(final String text) {
        String procesString = text;
        procesString = procesString.replaceAll("[Jj]e ", "vous ");
        procesString = procesString.replaceAll("([Jj]')", "vous ");
        procesString = procesString.replaceAll("([Mm]')", "vous ");
        procesString = procesString.replaceAll("([Tt]')", "m'");
        return procesString;
    }

    /**
     * Normalize the given text by replacing pronouns.
     *
     * @param text The text to normalize.
     * @return The normalized text.
     */
    public String replacePronounsSecondToFirst(final String text) {
        String procesString = text;
        procesString = procesString.replaceAll("[Vv]ous ", "je ");
        return procesString;
    }

    /**
     * .
     * Normalize the given text by verbs by the right conjugation
     *
     * @param text The text to normalize.
     * @return The normalized text.
     */
    public String verbsFirstToSecond(final String text) {
        String procesString = text;
        for (Verb verb : VerbList.getVerbs()) {
            procesString = procesString.replaceAll("\\b"
                    + verb.getFirstSingular() + "\\b", verb.getSecondPlural());
        }
        return procesString;
    }

    /**
     * Normalize the given text by the right conjugation.
     *
     * @param text The text to normalize.
     * @return The normalized text.
     */
    public String verbsSecondToFirst(final String text) {
        String procesString = text;
        String verbFirst = "";
        for (Verb verb : VerbList.getVerbs()) {
            verbFirst = convertVerb(verb.getSecondPlural());
            procesString = procesString.replaceAll("\\b"
                    + verb.getSecondPlural() + "\\b", verbFirst);
        }
        return procesString;
    }

    /**
     * Convert a verb from second-person plural to first-person singular conjugation.
     *
     * @param verb The verb in second-person plural conjugation.
     * @return The verb in first-person singular conjugation.
     */
    public String convertVerb(final String verb) {
        return VerbList.getVerbs().stream()
                .filter(v -> v.getSecondPlural().equals(verb))
                .findFirst()
                .get()
                .getFirstSingular();
    }

    /**
     * .
     * Replace possessive pronouns in the given text with their corresponding
     * English conjugations.
     *
     * @param text The text to process.
     * @return The text with possessive pronouns replaced.
     */
    public String replacePossessives(final String text) {
        Pattern pattern = Pattern.compile("\\bme\\b|\\bma\\b|\\bmes\\b|\\bmoi\\b|\\bm'a\\b",
                Pattern.CASE_INSENSITIVE);
        java.util.regex.Matcher matcher = pattern.matcher(text);

        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            String replacement;
            switch (matcher.group().toLowerCase()) {
                case "me":

                    replacement = (matcher.start() == 0 ? "Vous" : "vous");
                    break;
                case "ma":
                case "m'a":
                    replacement = (matcher.start() == 0 ? "Votre" : "votre");
                    break;
                case "mes":
                    replacement = (matcher.start() == 0 ? "Vos" : "vos");
                    break;

                default:
                    continue;
            }
            matcher.appendReplacement(sb, replacement);
        }
        matcher.appendTail(sb);

        return sb.toString();
    }

}
