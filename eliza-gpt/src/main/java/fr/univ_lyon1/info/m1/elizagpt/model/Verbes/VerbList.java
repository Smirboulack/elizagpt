package fr.univ_lyon1.info.m1.elizagpt.model.Verbes;

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
     * @throws CsvValidationException
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

    public String replacePronouns(String text) {
        text = text.replaceAll("[Jj]e ", "vous ");
        text = text.replaceAll("([Jj]')", "vous ");
        text = text.replaceAll("([Mm]')", "vous ");
        return text;
    }

    public String replaceVerbs(String text) {
        for (Verb verb : VerbList.getVerbs()) {
            text = text.replaceAll("\\b" + verb.getFirstSingular() + "\\b", verb.getSecondPlural());
        }
        return text;
    }

    public String replacePossessives(String text) {
        Pattern pattern = Pattern.compile("\\bme\\b|\\bma\\b|\\bmes\\b|\\bmoi\\b|\\bm'a\\b", Pattern.CASE_INSENSITIVE);
        java.util.regex.Matcher matcher = pattern.matcher(text);

        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            String replacement;
            switch (matcher.group().toLowerCase()) {
                case "me":
                    replacement = (matcher.start() == 0 ? "Vous" : "vous");
                    break;
                case "ma":
                    replacement = (matcher.start() == 0 ? "Votre" : "votre");
                    break;
                case "mes":
                    replacement = (matcher.start() == 0 ? "Vos" : "vos");
                    break;
                case "moi":
                    replacement = (matcher.start() == 0 ? "Vous" : "vous");
                    break;
                case "m'a":
                    replacement = (matcher.start() == 0 ? "Votre" : "votre");
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
