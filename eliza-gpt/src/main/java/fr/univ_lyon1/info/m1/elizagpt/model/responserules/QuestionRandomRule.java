package fr.univ_lyon1.info.m1.elizagpt.model.responserules;

import java.util.regex.Pattern;

import fr.univ_lyon1.info.m1.elizagpt.model.Processor;

/**
 * Interface for a rule that generates a response.
 */
public class QuestionRandomRule implements IResponseRule {
    /**
     * Check if the rule applies to the input.
     *
     * @param input le message à envoyer
     * @param processor le modèle
     * @return le résultat de la vérification
     */
    @Override
    public boolean appliesTo(final String input, final Processor processor) {
        return Pattern.compile(".*\\?\\s*.*$", Pattern.CASE_INSENSITIVE)
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
    public String generateResponse(final String input, final Processor processor) {
        String response = processor.pickRandom(new String[] {
                "Je vous renvoie la question.",
                "Ici, c'est moi qui pose les questions."
        });
        return response;
    }
}
