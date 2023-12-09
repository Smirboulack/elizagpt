package fr.univ_lyon1.info.m1.elizagpt.model.responserules;

import fr.univ_lyon1.info.m1.elizagpt.model.MessageProcessor;

/**
 * Interface for a rule that generates a response.
 */
public interface IResponseRule {
    /**
     * Check if the rule applies to the input.
     *
     * @param input le message à envoyer
     * @param processor le modèle
     * @return le résultat de la vérification
     */
    boolean appliesTo(String input, MessageProcessor processor);

    /**
     * Generate a response.
     *
     * @param input le message à envoyer
     * @param processor le modèle
     * @return le réponse générée
     */
    String generateResponse(String input, MessageProcessor processor);
}
