package fr.univ_lyon1.info.m1.elizagpt.model.responserules;

import java.util.regex.Pattern;

import fr.univ_lyon1.info.m1.elizagpt.model.Processor;

/**
 * Interface for a rule that generates a default response to any question.
 * 
 * @see IResponseRule
 */
public class QuestionRandomRule implements IResponseRule {
    /**
     * Check if the rule applies to the input.
     *
     * @param input     the message to check
     * @param processor the model
     * @return true if the rule applies, false otherwise
     */
    @Override
    public boolean appliesTo(final String input, final Processor processor) {
        return Pattern.compile(".*\\?\\s*.*$", Pattern.CASE_INSENSITIVE)
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
        return processor.pickRandom(new String[] {
                "Je vous renvoie la question.",
                "Ici, c'est moi qui pose les questions."
        });
    }
}
