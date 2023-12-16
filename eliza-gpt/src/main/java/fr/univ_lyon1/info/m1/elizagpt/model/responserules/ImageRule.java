package fr.univ_lyon1.info.m1.elizagpt.model.responserules;

import fr.univ_lyon1.info.m1.elizagpt.model.Processor;
import java.util.regex.Pattern;

/**
 * A response rule that generates a random response.
 */
public class ImageRule implements IResponseRule {

    /**
     * Check if the rule applies to the input.
     *
     * @param input     le message à envoyer
     * @param processor le modèle
     * @return le résultat de la vérification
     */
    @Override
    public boolean appliesTo(final String input, final Processor processor) {
        boolean pattern1 = Pattern.compile(".*\\.(png|jpg|jpeg|svg|gif)$", Pattern.CASE_INSENSITIVE)
                .matcher(input).matches();
        return pattern1;
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
                "Pas mal tout ça !",
                "C'est une belle image",
                "Un véritable chef d'oeuvre !"
        });
    }
}
