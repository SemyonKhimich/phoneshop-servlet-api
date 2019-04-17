package com.es.phoneshop.model.review;

import com.es.phoneshop.model.product.Product;

import java.util.List;

public interface ProductReviewService {
    ProductReview createProductReview(Product product);
    void addReview(ProductReview productReview, Review review) throws IncorrectRatingException;
}
