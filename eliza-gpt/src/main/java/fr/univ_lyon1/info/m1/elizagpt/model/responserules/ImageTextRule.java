package fr.univ_lyon1.info.m1.elizagpt.model.responserules;

import fr.univ_lyon1.info.m1.elizagpt.model.Processor;
import java.util.regex.Pattern;

/**
 * Interface for a rule that generates a response.
 */
public class ImageTextRule implements IResponseRule {
    /**
     * Check if the rule applies to the input.
     *
     * @param input     le message à envoyer
     * @param processor le modèle
     * @return le résultat de la vérification
     */
    @Override
    public boolean appliesTo(final String input, final Processor processor) {
        return Pattern.compile("text\\s*:\\s*.*\\s*image\\s*:\\s*.*", Pattern.CASE_INSENSITIVE)
                .matcher(input).matches();
    }

    /**
     * Generate a response.
     *
     * @param input     le message à envoyer
     * @param processor le modèle
     * @return la réponse générée
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
