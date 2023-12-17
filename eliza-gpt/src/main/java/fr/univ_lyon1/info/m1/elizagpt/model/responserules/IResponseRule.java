package fr.univ_lyon1.info.m1.elizagpt.model.responserules;

import fr.univ_lyon1.info.m1.elizagpt.model.Processor;

/**
 * Interface for a rule that generates a response.
 */
public interface IResponseRule {
    /**
     * Check if the rule applies to the input.
     *
     * @param input     the message to check
     * @param processor the model
     * @return true if the rule applies, false otherwise
     */
    boolean appliesTo(String input, Processor processor);

    /**
     * Generate a response.
     *
     * @param input     The input to generate a response for.
     * @param processor the model
     * @return The generated response.
     */
    String generateResponse(String input, Processor processor);
}
