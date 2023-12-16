package fr.univ_lyon1.info.m1.elizagpt.model.message;

/**
 * Class representing a chat message.
 */
public abstract class Message {
    private String id;
    private String author;
    private String date;

    /**
     * Constructor.
     * 
     * @param idm
     * @param text
     * @param author
     * @param date
     * @param style
     */
    public Message(final String id, final String author, final String date) {
        this.id = id;
        this.author = author;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getAuthor() {
        return author;
    }

    public void setDate(final String date) {
        this.date = date;
    }

    public void setAuthor(final String author) {
        this.author = author;
    }

    public abstract String toString();

    public abstract String[] parseMessageString();

}
