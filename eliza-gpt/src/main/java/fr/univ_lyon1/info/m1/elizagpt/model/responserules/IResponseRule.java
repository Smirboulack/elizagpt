package fr.univ_lyon1.info.m1.elizagpt.model.responserules;

import fr.univ_lyon1.info.m1.elizagpt.model.MessageProcessor;

/**
 * Interface for a rule that generates a response.
 */
public interface IResponseRule {
    /**
     * Check if the rule applies to the input.
     * 
     * @param input
     * @param processor
     * @return
     */
    boolean appliesTo(String input, MessageProcessor processor);

    /**
     * Generate a response.
     * 
     * @param input
     * @param processor
     * @return
     */
    String generateResponse(String input, MessageProcessor processor);
}
