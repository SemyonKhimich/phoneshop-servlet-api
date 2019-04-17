package com.es.phoneshop.model.review;

public class IncorrectRatingException extends Exception {
    public IncorrectRatingException(String message) {
        super(message);
    }
}
