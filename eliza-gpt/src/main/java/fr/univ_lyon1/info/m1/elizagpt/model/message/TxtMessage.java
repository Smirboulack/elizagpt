package fr.univ_lyon1.info.m1.elizagpt.model.message;

/**
 * .
 * This class is used to create message which include only a text
 * 
 * @see Message
 */
public class TxtMessage extends Message {
    private String text;

    /**
     * .
     * Constructor for a CombinedMessage
     * 
     * @param id     the id of the message
     * @param author the author of the message
     * @param date   the date of the message
     */
    public TxtMessage(final String id, final String author, final String date, final String txt) {
        super(id, author, date);
        this.text = txt;
    }

    /**
     * Getter for the message.
     * 
     * @return the message
     */
    public String getText() {
        return text;
    }

    /**
     * Setter for the message.
     * 
     * @param txt the new message
     */
    public void setText(final String txt) {
        this.text = txt;
    }

    /**
     * Used to turn a TxtMessage to his String version.
     * 
     * @return the TxtMessage as a String
     */
    public String toString() {
        return this.getId() + " : " + this.getAuthor() + " : "
                + this.getDate() + " : " + this.getText();
    }

    /**
     * Used to parse a TxtMessage from his String version.
     * 
     * @return an array of String containing the different fields of the message
     */
    @Override
    public String[] parseMessageString() {
        return this.toString().split(" : ", -1);
    }
}
