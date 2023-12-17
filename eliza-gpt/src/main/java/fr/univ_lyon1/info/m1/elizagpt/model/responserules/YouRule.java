package fr.univ_lyon1.info.m1.elizagpt.model.responserules;

import com.opencsv.exceptions.CsvValidationException;
import java.util.regex.Pattern;
import fr.univ_lyon1.info.m1.elizagpt.model.Processor;

/**
 * Interface for a rule that generates a response when the user says "you".
 */
public class YouRule implements IResponseRule {
    /**
     * Check if the rule applies to the input.
     * 
     * @param input     the message to check
     * @param processor the model
     * @return true if the rule applies, false otherwise
     */
    @Override
    public boolean appliesTo(final String input, final Processor processor) {
        return Pattern.compile("(Vous .*)", Pattern.CASE_INSENSITIVE)
                .matcher(input).matches();
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
            return startQuestion + processor.secondToFirstPerson(processor.normalize(input)) + " ?";
        } catch (CsvValidationException e) {
            e.printStackTrace();
        }

        return null;
    }
}
