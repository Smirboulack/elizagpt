package fr.univ_lyon1.info.m1.elizagpt.model.message;

public class CombinedMessage extends Message {
    private String message;
    private String imageUrl;

    public CombinedMessage(final String id, final String author, final String date, final String txt,
            final String imgUrl) {
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

    public String toString() {
        return this.getId() + " : " + this.getAuthor() + " : " + this.getDate() + " : "
                + this.getMessage() + " : " + this.getImageUrl();
    }

    @Override
    public String[] parseMessageString() {
        String[] parts = this.toString().split(" : ", -1);
        return parts;
    }

}
