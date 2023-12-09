package fr.univ_lyon1.info.m1.elizagpt.controller.searchStrategy;


public class ExactSentence extends Substring {
    @Override
    public boolean search(final String text, final String searchString) {
        return text.equals(searchString);
    }
}

