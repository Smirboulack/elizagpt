package fr.univ_lyon1.info.m1.elizagpt.model.message;

/**
 * Class representing a chat message.
 */
public abstract class Message {
    private final String id;
    private String author;
    private String date;

    /**
     * Constructor for a Message.
     *
     * @param id the id of the message
     * @param author the author of the message
     * @param date the date of the message
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

    /**.
     * Used to turn a Message to his String version
     * @return the Message as a String
     */
    public abstract String toString();

    /**.
     * Used to parse a message as an Array of String which contains every member data
     * @return an Array of String
     */
    public abstract String[] parseMessageString();

}
