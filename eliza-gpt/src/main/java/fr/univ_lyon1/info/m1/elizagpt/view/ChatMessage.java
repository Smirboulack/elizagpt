package fr.univ_lyon1.info.m1.elizagpt.view;

public class ChatMessage {
    private int id;
    private String text;
    private String author;
    private String date;
    private String style;

    public static final String BASE_STYLE = "-fx-padding: 8px; "
            + "-fx-margin: 5px; "
            + "-fx-background-radius: 5px;";
    public static final String USER_STYLE = "-fx-background-color: #A0E0A0; " + BASE_STYLE;
    public static final String ELIZA_STYLE = "-fx-background-color: #A0A0E0; " + BASE_STYLE;

    public ChatMessage(final int idm, final String text, final String author, final String date, final String style) {
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

    public void setText(String text) {
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

    public void setDate(String date) {
        this.date = date;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String toString() {
        return author + " : " + text + " (" + date + ")";
    }

}
