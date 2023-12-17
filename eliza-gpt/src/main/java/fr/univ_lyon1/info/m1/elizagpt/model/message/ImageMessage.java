package fr.univ_lyon1.info.m1.elizagpt.model.message;

/**
 * This class is used to create message which include only an image.
 * 
 * @see Message
 */
public class ImageMessage extends Message {
    private String imageUrl;

    /**
     * Constructor for a CombinedMessage.
     * 
     * @param id     the id of the message
     * @param author the author of the message
     * @param date   the date of the message
     * @param imgUrl the path to the image of the message
     */
    public ImageMessage(final String id, final String author, final String date,
            final String imgUrl) {
        super(id, author, date);
        this.imageUrl = imgUrl;
    }

    /**
     * Getter for the image url.
     * 
     * @return the image url
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * Setter for the image url.
     * 
     * @param imageUrl the new image url
     */
    public void setImageUrl(final String imageUrl) {
        this.imageUrl = imageUrl;
    }

    /**
     * .
     * Used to turn an ImageMessage to his String version
     * 
     * @return the ImageMessage as a String
     */
    public String toString() {
        return this.getId() + " : " + this.getAuthor() + " : "
                + this.getDate() + " : " + this.getImageUrl();
    }

    /**
     * Used to parse an ImageMessage from his String version.
     * 
     * @return an array of String containing the different fields of the message
     */
    @Override
    public String[] parseMessageString() {
        return this.toString().split(" : ", -1);
    }

}
