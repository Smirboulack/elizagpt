package fr.univ_lyon1.info.m1.elizagpt.model.responserules;

import fr.univ_lyon1.info.m1.elizagpt.model.Processor;
import java.util.regex.Pattern;

/**
 * A response rule that generates a response for an image only message.
 * 
 * @see IResponseRule
 */
public class ImageRule implements IResponseRule {

    /**
     * Check if the rule applies to the input.
     *
     * @param input     the message to check
     * @param processor the model
     * @return true if the rule applies, false otherwise
     */
    @Override
    public boolean appliesTo(final String input, final Processor processor) {
        return Pattern.compile(".*\\.(png|jpg|jpeg|svg|gif)$", Pattern.CASE_INSENSITIVE)
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
        return processor.pickRandom(new String[] {
                "Pas mal tout ça !",
                "C'est une belle image",
                "Un véritable chef d'oeuvre !"
        });
    }
}
