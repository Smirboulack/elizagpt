package fr.univ_lyon1.info.m1.elizagpt.model.responserules;

import java.util.regex.Pattern;
import fr.univ_lyon1.info.m1.elizagpt.model.MessageProcessor;

/**
 * Interface for a rule that generates a response.
 */
public class WhatsNameRule implements IResponseRule {
    /**
     * Check if the rule applies to the input.
     * 
     * @param input
     * @param processor
     * @return
     */
    @Override
    public boolean appliesTo(final String input, final MessageProcessor processor) {
        return Pattern.compile("Quel est mon nom \\?", Pattern.CASE_INSENSITIVE)
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
        String name = processor.getName();
        if (name != null) {
            return "Votre nom est " + name + ".";
        } else {
            return "Je ne connais pas votre nom.";
        }
    }
}
