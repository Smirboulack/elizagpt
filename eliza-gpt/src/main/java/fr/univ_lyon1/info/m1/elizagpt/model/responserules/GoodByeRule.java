package fr.univ_lyon1.info.m1.elizagpt.model.responserules;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fr.univ_lyon1.info.m1.elizagpt.model.Processor;

/**
 * Interface for a rule that generates a response for a good bye message.
 * 
 * @see IResponseRule
 */
public class GoodByeRule implements IResponseRule {

    /**
     * Check if the rule applies to the input.
     *
     * @param input     the message to check
     * @param processor the model
     * @return true if the rule applies, false otherwise
     */
    @Override
    public boolean appliesTo(final String input, final Processor processor) {
        return Pattern.compile("(?i)^au revoir\\.$", Pattern.CASE_INSENSITIVE)
                .matcher(input)
                .matches();
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
        Matcher matcher = Pattern.compile("(?i)^au revoir\\.$", Pattern.CASE_INSENSITIVE)
                .matcher(input);
        if (matcher.matches()) {
            String str1 = "Au revoir";
            String str2 = "Oh non, c'est trop triste de se quitter !";
            String str3 = "Bon débarras !";
            if (processor.getName() != null) {
                str1 += " " + processor.getName() + ".";
                str3 = "Bon débarras, n'oublie pas de commit + push avant de partir "
                        + processor.getName() + ".";
            }
            return (processor.pickRandom(new String[] {
                    str1,
                    str2,
                    str3
            }));
        }
        return null;
    }
}
