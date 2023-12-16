package fr.univ_lyon1.info.m1.elizagpt.model.message;

public class TxtMessage extends Message {
    private String text;

    public TxtMessage(String id, String author, String date, final String txt) {
        super(id, author, date);
        this.text = txt;
    }

    public String getText() {
        return text;
    }

    public void setText(final String txt) {
        this.text = txt;
    }

    public String toString() {
        return this.getId() + " : " + this.getAuthor() + " : " + this.getDate() + " : " + this.getText();
    }

    @Override
    public String[] parseMessageString() {
        String[] parts = this.toString().split(" : ", -1);
        return parts;
    }
}
