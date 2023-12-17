package fr.univ_lyon1.info.m1.elizagpt.model.responserules;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fr.univ_lyon1.info.m1.elizagpt.model.Processor;

/**
 * Interface for a rule that generates a response to the question "Qui est le plus ... ?".
 * 
 * @see IResponseRule
 */
public class WhoIsTheMostRule implements IResponseRule {
    /**
     * Check if the rule applies to the input.
     *
     * @param input     the message to check
     * @param processor the model
     * @return true if the rule applies, false otherwise
     */
    @Override
    public boolean appliesTo(final String input, final Processor processor) {
        return Pattern.compile("Qui est le plus (.*) \\?", Pattern.CASE_INSENSITIVE)
                .matcher(input)
                .matches();
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
        Matcher matcher = Pattern.compile("Qui est le plus (.*) \\?", Pattern.CASE_INSENSITIVE)
                .matcher(input);
        if (matcher.matches()) {
            return "Le plus " + matcher.group(1)
                    + " est bien sûr votre enseignant de MIF01 !";
        }
        return null;
    }
}
