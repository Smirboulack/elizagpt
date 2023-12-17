package fr.univ_lyon1.info.m1.elizagpt.model.message;

/**
 * Class representing a message.
 * This class is abstract and is used to create different type of message.
 * Composite pattern is used to create a tree of message.
 */
public abstract class Message {
    private final String id;
    private String author;
    private String date;

    /**
     * Constructor for a Message.
     *
     * @param id     the id of the message
     * @param author the author of the message
     * @param date   the date of the message
     */
    public Message(final String id, final String author, final String date) {
        this.id = id;
        this.author = author;
        this.date = date;
    }

    /**
     * Getter for the id.
     *
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Getter for the date.
     *
     * @return the date
     */
    public String getDate() {
        return date;
    }

    /**
     * Getter for the author.
     *
     * @return the author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Setter for the date.
     *
     * @param date the new date
     */
    public void setDate(final String date) {
        this.date = date;
    }

    /**
     * Setter for the author.
     *
     * @param author the new author
     */
    public void setAuthor(final String author) {
        this.author = author;
    }

    /**
     * .
     * Used to turn a Message to his String version
     * 
     * @return the Message as a String
     */
    public abstract String toString();

    /**
     * 
     * Used to parse a message as an Array of String which contains every member.
     * 
     * @return an Array of String
     */
    public abstract String[] parseMessageString();

}
