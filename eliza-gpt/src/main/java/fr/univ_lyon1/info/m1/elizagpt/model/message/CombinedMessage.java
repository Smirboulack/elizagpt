package fr.univ_lyon1.info.m1.elizagpt.model.message;

/**.
 * This class is used to create message which include an image and a text
 */
public class CombinedMessage extends Message {
    private String message;
    private String imageUrl;

    /**.
     * Constructor for a CombinedMessage
     * @param id the id of the message
     * @param author the author of the message
     * @param date the date of the message
     * @param txt the text of the message
     * @param imgUrl the path to the image of the message
     */
    public CombinedMessage(final String id, final String author, final String date,
                           final String txt, final String imgUrl) {
        super(id, author, date);
        this.message = txt;
        this.imageUrl = imgUrl;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(final String txt) {
        this.message = txt;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(final String imageUrl) {
        this.imageUrl = imageUrl;
    }

    /**.
     * Used to turn a CombinedMessage to his String version
     * @return the CombinedMessage as a String
     */
    public String toString() {
        return this.getId() + " : " + this.getAuthor() + " : " + this.getDate() + " : "
                + this.getMessage() + " : " + this.getImageUrl();
    }

    @Override
    public String[] parseMessageString() {
        return this.toString().split(" : ", -1);
    }

}
