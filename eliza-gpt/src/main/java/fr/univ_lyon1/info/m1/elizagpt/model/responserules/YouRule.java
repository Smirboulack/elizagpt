package fr.univ_lyon1.info.m1.elizagpt.model.responserules;

import com.opencsv.exceptions.CsvValidationException;
import java.util.regex.Pattern;
import fr.univ_lyon1.info.m1.elizagpt.model.Processor;

public class YouRule implements IResponseRule{
        /**
     * Check if the rule applies to the input.
     * 
     * @param input le message à envoyer
     * @param processor le modèle
     * @return le résultat de la vérification
     */
    @Override
    public boolean appliesTo(final String input, final Processor processor) {
        boolean pattern1 = Pattern.compile("(Vous .*)", Pattern.CASE_INSENSITIVE)
                .matcher(input).matches();
        return pattern1;
    }

    /**
     * Generate a response.
     * 
     * @param input le message à envoyer
     * @param processor le modèle
     * @return la réponse générée
     */
    @Override
    public String generateResponse(final String input, final Processor processor) {
        String startQuestion = processor.pickRandom(new String[] {
                "Pourquoi dites-vous que ",
                "Pourquoi pensez-vous que ",
                "Êtes-vous sûr que ",
        });
        try {
            return startQuestion + processor.secondToFirstPerson(processor.normalize(input)) + " ?";
        } catch (CsvValidationException e) {
            e.printStackTrace();
        }

        return null;
    }
}
