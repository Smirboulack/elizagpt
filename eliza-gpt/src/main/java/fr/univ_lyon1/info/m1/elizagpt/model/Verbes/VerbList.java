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
                CSVReader csvReader = new CSVReader(reader);) {
            String[] nextRecord;
            while ((nextRecord = csvReader.readNext()) != null) {
                Verb verb = new Verb(nextRecord[5], nextRecord[9]);
                System.out.print(nextRecord[5]);
                System.out.print(" ");
                System.out.println(nextRecord[9]);
                verbs.add(verb);
            }
        } catch (IOException e) {
        }
    }

    public static List<Verb> getVerbs() {
        return verbs;
    }

}
