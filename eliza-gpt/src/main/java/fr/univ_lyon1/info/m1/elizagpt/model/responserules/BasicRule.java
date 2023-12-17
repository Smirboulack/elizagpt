package fr.univ_lyon1.info.m1.elizagpt.model.responserules;

import fr.univ_lyon1.info.m1.elizagpt.model.Processor;
import java.util.regex.Pattern;


import com.opencsv.exceptions.CsvValidationException;

/**
 * A response rule that generates a basic response.
 * 
 * @see IResponseRule
 */
public class BasicRule implements IResponseRule {

    /**
     * Check if the rule applies to the input.
     * 
     * @param input     The input to check.
     * @param processor the model
     * @return true if the rule applies, false otherwise.
     */
    @Override
    public boolean appliesTo(final String input, final Processor processor) {
        boolean pattern1 = Pattern.compile("Je (?!.*\\?$).*", Pattern.CASE_INSENSITIVE)
                .matcher(input).matches();
        boolean pattern2 = Pattern.compile("J'(?!.*\\?$).*", Pattern.CASE_INSENSITIVE)
                .matcher(input).matches();
        boolean pattern3 = Pattern.compile("T'(?!.*\\?$).*", Pattern.CASE_INSENSITIVE)
                .matcher(input).matches();
        return pattern1 || pattern2 || pattern3;
    }

    /**
     * Generate a response.
     * 
     * @param input     The input to generate a response for.
     * @param processor the model
     * @return The generated response.
     */
    @Override
    public String generateResponse(final String input, final Processor processor) {
        String startQuestion = processor.pickRandom(new String[] {
                "Pourquoi dites-vous que ",
                "Pourquoi pensez-vous que ",
                "Êtes-vous sûr que ",
        });
        try {
            return startQuestion + processor.firstToSecondPerson(input) + " ?";
        } catch (CsvValidationException e) {
            e.printStackTrace();
        }

        return null;
    }
}
