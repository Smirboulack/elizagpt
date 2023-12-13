package fr.univ_lyon1.info.m1.elizagpt.model.responserules;

import fr.univ_lyon1.info.m1.elizagpt.model.MessageProcessor;
import java.util.regex.Pattern;

import com.opencsv.exceptions.CsvValidationException;
import javafx.scene.image.Image;

/**
 * A response rule that generates a random response.
 */
public class ImageRule implements IResponseRule {

    /**
     * Check if the rule applies to the input.
     *
     * @param input le message à envoyer
     * @param processor le modèle
     * @return le résultat de la vérification
     */
    @Override
    public boolean appliesTo(final String input, final MessageProcessor processor) {
        /*boolean pattern1 = Pattern.compile("(Je .*)", Pattern.CASE_INSENSITIVE)
                .matcher(input).matches();
        boolean pattern2 = Pattern.compile("(J'.*)", Pattern.CASE_INSENSITIVE)
                .matcher(input).matches();
        boolean pattern3 = Pattern.compile("([Mm]'.*)", Pattern.CASE_INSENSITIVE)
                .matcher(input).matches();
        return pattern1 || pattern2 || pattern3;*/
        return false;
    }

    @Override
    public String generateResponse(String input, MessageProcessor processor) {
        return null;
    }

    /**
     * Generate a response.
     *
     * @param input le message à envoyer
     * @param processor le modèle
     * @return la réponse générée
     */
    public String generateResponse(final String input, final Image imageFile, final MessageProcessor processor) {
        if(input.isEmpty() && imageFile != null) { //S'il y a une image et pas de texte
            System.out.println("ImageRule: Image sans texte");
            return processor.pickRandom(new String[] {
                    "Pas mal tout ça !",
                    "C'est une belle image",
                    "Un véritable chef d'oeuvre !"
            });
        }
        if (input != null && imageFile != null) { //S'il y a du texte et une image
            System.out.println("ImageRule: Image avec texte");
            return "Tais toi et laisse moi comtempler cette art !";
        }
        return input;
    }
}
