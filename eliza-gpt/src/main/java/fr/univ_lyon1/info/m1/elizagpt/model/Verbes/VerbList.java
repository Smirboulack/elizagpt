package fr.univ_lyon1.info.m1.elizagpt.model.Verbes;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.json.*;

/**
 * A list of verbs.
 */
public class VerbList {

    private static final List<Verb> verbs = new ArrayList<>();

    /*
     * static {
     * try {
     * FileReader reader = new FileReader("src/main/resources/verbs.json");
     * JSONTokener tokener = new JSONTokener(reader);
     * JSONArray array = new JSONArray(tokener);
     * for (int i = 0; i < array.length(); i++) {
     * JSONObject obj = array.getJSONObject(i);
     * verbs.add(new Verb(obj.getString("firstSingular"),
     * obj.getString("secondPlural")));
     * }
     * 
     * } catch (Exception e) {
     * e.printStackTrace();
     * }
     * }
     */

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
                if (nextRecord.length > 9) { // Vérifiez si la ligne a suffisamment de colonnes
                    String firstSingular = nextRecord[5]; // Supposons que c'est la forme au premier singulier
                    String secondPlural = nextRecord[9]; // Supposons que c'est la forme au second pluriel
                    Verb verb = new Verb(firstSingular, secondPlural);
                    verbs.add(verb);
                } else {
                    // Gérez le cas où il n'y a pas assez de données dans la ligne
                    // Par exemple, enregistrer un avertissement ou ignorer la ligne
                }
            }
        } catch (IOException e) {
            // Gérez l'exception ici, par exemple en l'enregistrant ou en la propageant
            throw new RuntimeException("Erreur lors de la lecture du fichier CSV: " + filePath, e);
        }
    }

    public static List<Verb> getVerbs() {
        return verbs;
    }

}
