package fr.univ_lyon1.info.m1.elizagpt.model.responserules;

import java.util.regex.Pattern;
import fr.univ_lyon1.info.m1.elizagpt.model.Processor;

/**
 * Interface for a rule that generates a response.
 */
public class WhatsNameRule implements IResponseRule {
    /**
     * Check if the rule applies to the input.
     *
     * @param input     le message à envoyer
     * @param processor le modèle
     * @return le résultat de la vérification
     */
    @Override
    public boolean appliesTo(final String input, final Processor processor) {
        boolean pat1 = Pattern.compile("Quel est mon nom\\s*\\?\\s*.*$", Pattern.CASE_INSENSITIVE)
                .matcher(input)
                .matches();
        boolean pat2 = Pattern.compile("Comment je m'appelle\\s*\\?\\s*.*$",
                        Pattern.CASE_INSENSITIVE)
                .matcher(input)
                .matches();
        return pat1 || pat2;
    }

    /**
     * Generate a response.
     *
     * @param input     le message à envoyer
     * @param processor le modèle
     * @return le réponse générée
     */
    @Override
    public String generateResponse(final String input, final Processor processor) {
        String name = processor.getName();
        if (name != null) {
            return "Votre nom est " + name + ".";
        } else {
            return "Je ne connais pas votre nom.";
        }
    }
}
