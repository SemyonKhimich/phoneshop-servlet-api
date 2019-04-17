package com.es.phoneshop.model.review;

import com.es.phoneshop.model.product.Product;

import java.util.concurrent.atomic.AtomicLong;

public class ProductReviewServiceImpl implements ProductReviewService {
    private static final ProductReviewService instance = new ProductReviewServiceImpl();
    private final AtomicLong atomicLong;

    private ProductReviewServiceImpl() {
        atomicLong = new AtomicLong(0);
    }

    public static ProductReviewService getInstance() {
        return instance;
    }

    @Override
    public ProductReview createProductReview(Product product) {
        ProductReview productReview = new ProductReview();
        productReview.setProduct(product);
        return productReview;
    }

    @Override
    public void addReview(ProductReview productReview, Review review) throws IncorrectRatingException {
        if (review.getRating() < 1 || review.getRating() > 5) {
            throw new IncorrectRatingException("rating must be from 1 to 5");
        }
        review.setId(atomicLong.incrementAndGet());
        productReview.getReviews().add(review);
    }
}
