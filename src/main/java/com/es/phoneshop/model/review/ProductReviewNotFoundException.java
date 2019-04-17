package com.es.phoneshop.model.review;

public class ProductReviewNotFoundException extends RuntimeException {
    public ProductReviewNotFoundException(String message) {
        super(message);
    }
}
