package fr.univ_lyon1.info.m1.elizagpt.model.responserules;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fr.univ_lyon1.info.m1.elizagpt.model.MessageProcessor;

/**
 * Interface for a rule that generates a response to the question "Qui est le
 * plus ...".
 */
public class WhoIsTheMostRule implements IResponseRule {
    /**
     * Check if the rule applies to the input.
     * 
     * @param input
     * @param processor
     * @return
     */
    @Override
    public boolean appliesTo(final String input, final MessageProcessor processor) {
        return Pattern.compile("Qui est le plus (.*) \\?", Pattern.CASE_INSENSITIVE)
                .matcher(input)
                .matches();
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
        Matcher matcher = Pattern.compile("Qui est le plus (.*) \\?", Pattern.CASE_INSENSITIVE)
                .matcher(input);
        if (matcher.matches()) {
            return "Le plus " + matcher.group(1)
                    + " est bien sûr votre enseignant de MIF01 !";
        }
        return null;
    }
}
