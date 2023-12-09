package fr.univ_lyon1.info.m1.elizagpt.model.responserules;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fr.univ_lyon1.info.m1.elizagpt.model.MessageProcessor;

/**
 * Interface for a rule that generates a response.
 */
public class NameRule implements IResponseRule {

    /**
     * Check if the rule applies to the input.
     *
     * @param input le message à envoyer
     * @param processor le modèle
     * @return le résultat de la vérification
     */
    @Override
    public boolean appliesTo(final String input, final MessageProcessor processor) {
        return Pattern.compile(".*Je m'appelle (.*)\\.", Pattern.CASE_INSENSITIVE)
                .matcher(input)
                .matches();
    }

    /**
     * Generate a response.
     *
     * @param input le message à envoyer
     * @param processor le modèle
     * @return le réponse générée
     */
    @Override
    public String generateResponse(final String input, final MessageProcessor processor) {
        Matcher matcher = Pattern.compile(".*Je m'appelle (.*)\\.", Pattern.CASE_INSENSITIVE)
                .matcher(input);
        if (matcher.matches()) {
            String name = matcher.group(1);
            processor.setName(name);
            return "Bonjour " + name + ".";
        }
        return null;
    }
}
