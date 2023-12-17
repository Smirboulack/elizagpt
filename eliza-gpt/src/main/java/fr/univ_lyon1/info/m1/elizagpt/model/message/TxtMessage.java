package fr.univ_lyon1.info.m1.elizagpt.model.message;

/**.
 * This class is used to create message which include only a text
 */
public class TxtMessage extends Message {
    private String text;

    /**.
     * Constructor for a CombinedMessage
     * @param id the id of the message
     * @param author the author of the message
     * @param date the date of the message
     */
    public TxtMessage(final String id, final String author, final String date, final String txt) {
        super(id, author, date);
        this.text = txt;
    }

    public String getText() {
        return text;
    }

    public void setText(final String txt) {
        this.text = txt;
    }

    /**.
     * Used to turn a TxtMessage to his String version
     * @return the TxtMessage as a String
     */
    public String toString() {
        return this.getId() + " : " + this.getAuthor() + " : "
                + this.getDate() + " : " + this.getText();
    }

    @Override
    public String[] parseMessageString() {
        return this.toString().split(" : ", -1);
    }
}
