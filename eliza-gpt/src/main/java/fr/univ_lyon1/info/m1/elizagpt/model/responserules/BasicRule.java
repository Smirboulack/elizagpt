package fr.univ_lyon1.info.m1.elizagpt.model.responserules;

import fr.univ_lyon1.info.m1.elizagpt.model.MessageProcessor;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.opencsv.exceptions.CsvValidationException;

/**
 * A response rule that generates a random response.
 */
public class BasicRule implements IResponseRule {

    /**
     * Check if the rule applies to the input.
     * 
     * @param input
     * @param processor
     * @return
     */
    @Override
    public boolean appliesTo(final String input, final MessageProcessor processor) {
        return Pattern.compile("(Je .*)", Pattern.CASE_INSENSITIVE)
                .matcher(input).matches();
    }

    /**
     * Generate a response.
     * 
     * @param input
     * @param processor
     * @return
     */
    @Override
    public String generateResponse(final String input, final MessageProcessor processor) {
        Matcher matcher = Pattern.compile("(Je .*)", Pattern.CASE_INSENSITIVE)
                .matcher(input);
        if (matcher.matches()) {
            String startQuestion = processor.pickRandom(new String[] {
                    "Pourquoi dites-vous que ",
                    "Pourquoi pensez-vous que ",
                    "Êtes-vous sûr que ",
            });
            try {
                return startQuestion + processor.firstToSecondPerson(matcher.group(1) + " ?");
            } catch (CsvValidationException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
