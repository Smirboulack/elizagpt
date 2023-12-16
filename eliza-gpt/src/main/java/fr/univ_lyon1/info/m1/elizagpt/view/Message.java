package fr.univ_lyon1.info.m1.elizagpt.view;


/**
 * Class representing a chat message.
 */
public class Message {
    private final int id;
    private String text;
    private String author;
    private String date;
    private String style;

    public static final String BASE_STYLE = "-fx-padding: 8px; "
            + "-fx-margin: 5px; "
            + "-fx-background-radius: 5px;";
    public static final String USER_STYLE = "-fx-background-color: #A0E0A0; " + BASE_STYLE;
    public static final String ELIZA_STYLE = "-fx-background-color: #A0A0E0; " + BASE_STYLE;

    /**
     * Constructor.
     * 
     * @param idm the id of the message
     * @param text the text of the message
     * @param author the author of the message
     * @param date the date of the message
     * @param style the style of the message
     */
    public Message(final int idm, final String text, final String author, final String date,
            final String style) {
        this.id = idm;
        this.text = text;
        this.author = author;
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

}
