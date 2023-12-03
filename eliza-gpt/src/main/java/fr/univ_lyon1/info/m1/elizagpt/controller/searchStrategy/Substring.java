package fr.univ_lyon1.info.m1.elizagpt.controller.searchStrategy;

public class Substring implements SearchStrategy {
    @Override
    public boolean search(String text, String searchString) {
        return text.contains(searchString);
    }
}
