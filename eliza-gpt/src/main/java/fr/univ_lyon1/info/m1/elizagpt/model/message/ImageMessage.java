package fr.univ_lyon1.info.m1.elizagpt.model.message;

/**.
 * This class is used to create message which include only an image
 */
public class ImageMessage extends Message {
    private String imageUrl;

    /**.
     * Constructor for a CombinedMessage
     * @param id the id of the message
     * @param author the author of the message
     * @param date the date of the message
     * @param imgUrl the path to the image of the message
     */
    public ImageMessage(final String id, final String author, final String date,
                        final String imgUrl) {
        super(id, author, date);
        this.imageUrl = imgUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(final String imageUrl) {
        this.imageUrl = imageUrl;
    }

    /**.
     * Used to turn an ImageMessage to his String version
     * @return the ImageMessage as a String
     */
    public String toString() {
        return this.getId() + " : " + this.getAuthor() + " : "
                + this.getDate() + " : " + this.getImageUrl();
    }

    @Override
    public String[] parseMessageString() {
        return this.toString().split(" : ", -1);
    }

}
