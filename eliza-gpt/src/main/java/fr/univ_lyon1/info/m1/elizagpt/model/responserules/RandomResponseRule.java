package fr.univ_lyon1.info.m1.elizagpt.model.responserules;

import fr.univ_lyon1.info.m1.elizagpt.model.Processor;

/**
 * A response rule that generates a random response.
 */
public class RandomResponseRule implements IResponseRule {
    /**
     * Check if the rule applies to the input.
     *
     * @param input     the message to check
     * @param processor the model
     * @return true if the rule applies, false otherwise
     */
    @Override
    public boolean appliesTo(final String input, final Processor processor) {
        return true;
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
        String[] randomResponses = {
                "Il fait beau aujourd'hui, vous ne trouvez pas ?",
                "Je ne comprends pas.",
                "Hmmm, hmm ...",
                "Qu'est-ce qui vous fait dire cela ?",
        };
        if (processor.getName() != null) {
            randomResponses = new String[] {
                    "Il fait beau aujourd'hui, vous ne trouvez pas ?",
                    "Je ne comprends pas.",
                    "Hmmm, hmm ...",
                    "Qu'est-ce qui vous fait dire cela, " + processor.getName() + " ?",
            };
        }
        return processor.pickRandom(randomResponses);
    }

}
