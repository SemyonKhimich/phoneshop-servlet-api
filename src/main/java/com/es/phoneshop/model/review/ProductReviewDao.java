package com.es.phoneshop.model.review;

import java.util.List;

public interface ProductReviewDao {
    void save(ProductReview productReview);

    ProductReview getProductReview(Long id);

    List<ProductReview> getProductReviews();

}
