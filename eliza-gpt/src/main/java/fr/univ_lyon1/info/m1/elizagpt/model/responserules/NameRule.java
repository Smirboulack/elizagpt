package fr.univ_lyon1.info.m1.elizagpt.model.responserules;

import java.util.regex.Pattern;

import fr.univ_lyon1.info.m1.elizagpt.model.Processor;

/**
 * Interface for a rule that generates a response to give the name of the user.
 * 
 * @see IResponseRule
 */
public class NameRule implements IResponseRule {

    /**
     * Check if the rule applies to the input.
     *
     * @param input     the message to check
     * @param processor the model
     * @return true if the rule applies, false otherwise
     */
    @Override
    public boolean appliesTo(final String input, final Processor processor) {
        boolean pat1 = Pattern.compile(".*Je m'appelle (.*)\\.", Pattern.CASE_INSENSITIVE)
                .matcher(input)
                .matches();
        boolean pat2 = Pattern.compile(".*Mon nom est (.*)\\.", Pattern.CASE_INSENSITIVE)
                .matcher(input)
                .matches();
        return pat1 || pat2;
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
        String name = input.substring(input.lastIndexOf(" ") + 1, input.lastIndexOf("."));
        processor.setName(name);
        return "Bonjour " + name + ".";
    }
}
