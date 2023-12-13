package fr.univ_lyon1.info.m1.elizagpt.view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


/**
 * Class representing a chat message.
 */
public class ChatMessage {
    private final int id;
    private String text;
    private String author;
    private String date;
    private String style;
    private ImageView image = null;

    public static final String BASE_STYLE = "-fx-padding: 8px; "
            + "-fx-margin: 5px; "
            + "-fx-background-radius: 5px;";
    public static final String USER_STYLE = "-fx-background-color: #A0E0A0; " + BASE_STYLE;
    public static final String ELIZA_STYLE = "-fx-background-color: #A0A0E0; " + BASE_STYLE;

    /**
     * Constructor.
     * 
     * @param idm
     * @param text
     * @param author
     * @param date
     * @param style
     */
    public ChatMessage(final int idm, final String text, final ImageView i, final String author, final String date,
    final String style) {
        this.id = idm;
        this.text = text;
        this.author = author;
        this.image = i;
        if (i != null) {
            i.setFitWidth(80);
            i.setFitHeight(80);
        }
        this.date = date;
        this.style = style;
    }


    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public void setText(final String text) {
        this.text = text;
    }

    public String getDate() {
        return date;
    }

    public String getAuthor() {
        return author;
    }

    public String getStyle() {
        return style;
    }

    public void setDate(final String date) {
        this.date = date;
    }

    public void setAuthor(final String author) {
        this.author = author;
    }

    public void setStyle(final String style) {
        this.style = style;
    }

    @Override
    public String toString() {
        return author + " : " + text + " (" + date + ")";
    }

    public ImageView getImage() {
        return image;
    }

    public void setImage(ImageView image) {
        this.image = image;
    }

}
