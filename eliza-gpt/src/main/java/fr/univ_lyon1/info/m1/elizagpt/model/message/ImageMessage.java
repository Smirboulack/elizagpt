package fr.univ_lyon1.info.m1.elizagpt.model.message;

public class ImageMessage extends Message {
    private String imageUrl;

    public ImageMessage(String id, String author, String date, final String imgUrl) {
        super(id, author, date);
        this.imageUrl = imgUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(final String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String toString() {
        return this.getId() + " : " + this.getAuthor() + " : " + this.getDate() + " : " + this.getImageUrl();
    }

    @Override
    public String[] parseMessageString() {
        String[] parts = this.toString().split(" : ", -1);
        return parts;
    }

}
