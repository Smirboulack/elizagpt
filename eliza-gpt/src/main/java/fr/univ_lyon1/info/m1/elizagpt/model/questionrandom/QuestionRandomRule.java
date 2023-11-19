package fr.univ_lyon1.info.m1.elizagpt.model.questionrandom;

import java.util.regex.Pattern;

import fr.univ_lyon1.info.m1.elizagpt.model.MessageProcessor;
import fr.univ_lyon1.info.m1.elizagpt.model.responserules.IResponseRule;

/**
 * Interface for a rule that generates a response.
 */
public class QuestionRandomRule implements IResponseRule {
    /**
     * Check if the rule applies to the input.
     * 
     * @param input
     * @param processor
     * @return
     */
    @Override
    public boolean appliesTo(final String input, final MessageProcessor processor) {
        return Pattern.compile(".*\\?$", Pattern.CASE_INSENSITIVE)
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
        String response = processor.pickRandom(new String[] {
                "Je vous renvoie la question.",
                "Ici, c'est moi qui pose les questions."
        });
        return response;
    }
}
