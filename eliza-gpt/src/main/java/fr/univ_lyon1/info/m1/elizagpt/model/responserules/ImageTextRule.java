package fr.univ_lyon1.info.m1.elizagpt.model.responserules;

import fr.univ_lyon1.info.m1.elizagpt.model.Processor;
import java.util.regex.Pattern;

/**
 * Interface for a rule that generates a response for a message containing an
 * image and a text.
 * 
 * The message must be of the form "text: <text> image: <image>".
 * 
 * @see IResponseRule
 */
public class ImageTextRule implements IResponseRule {
    /**
     * Check if the rule applies to the input.
     *
     * @param input     the message to check
     * @param processor the model
     * @return true if the rule applies, false otherwise
     */
    @Override
    public boolean appliesTo(final String input, final Processor processor) {
        String regex = "text\\s*:\\s*.*\\s*image\\s*:\\s*.+\\.(png|jpeg|jpg|svg|bmp)$";
        return Pattern.compile(regex, Pattern.CASE_INSENSITIVE).matcher(input).matches();
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
                "Laissez-moi plutôt contempler cette image.",
                "Je préfère regarder cette image que de vous répondre.",
                "Je ne sais pas quoi vous répondre, je suis trop occupé à regarder cette image.",
        });
    }
}
