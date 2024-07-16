package pl.edu.wszib.game.store.validators;

import pl.edu.wszib.game.store.exceptions.GameValidationException;

public class GameValidator {
    public static String hasText = ".+";

    public static void validateTitle(String title) {
        if(!title.matches(hasText)) {
            throw new GameValidationException();
        }
    }

    public static void validatePublisher(String publisher) {
        if(!publisher.matches(hasText)) {
            throw new GameValidationException();
        }
    }

    public static void validateTags(String tags) {
        // get RPG, Open World, FPS, MMO tags and check if they are separated with commas
        String tagsPattern = "^[a-zA-Z]+(,[a-zA-Z]+)*$";
        String tagsNoSpaces = tags.replaceAll("\\s", "");
        if (!tagsNoSpaces.matches(tagsPattern)) {
            throw new GameValidationException();
        }
    }

    public static void validateDescription(String description) {
        if(description.isBlank() || description.length() < 10
                || !description.matches(hasText)) {
            throw new GameValidationException();
        }
    }

    public static void validatePrice(Double price) {
        if(price.isNaN() || price < 0) {
            throw new GameValidationException();
        }
    }

    public static void validateImageSize(long size) {
        // 5242880 equals 5MB
        // should block images with resolution greater than 4K
        if(size > 5242880) {
            throw new GameValidationException();
        }
    }
}
