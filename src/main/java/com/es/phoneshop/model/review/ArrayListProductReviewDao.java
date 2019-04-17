package com.es.phoneshop.model.review;

import java.util.ArrayList;
import java.util.List;

public class ArrayListProductReviewDao implements ProductReviewDao {
    private List<ProductReview> productReviews;
    private static final ProductReviewDao instance = new ArrayListProductReviewDao();

    private ArrayListProductReviewDao() {
        productReviews = new ArrayList<>();
    }

    public static ProductReviewDao getInstance() {
        return instance;
    }

    @Override
    public synchronized void save(ProductReview productReview) {
        if (productReview != null && productReviews.stream().noneMatch(element -> element.getProduct().getId().equals(productReview.getProduct().getId()))) {
            productReviews.add(productReview);
        }
    }

    @Override
    public synchronized ProductReview getProductReview(Long id) {
        return productReviews.stream()
                .filter(productReview -> productReview.getProduct().getId().equals(id))
                .findFirst().orElseThrow(() -> new ProductReviewNotFoundException("id = " + id));

    }

    @Override
    public List<ProductReview> getProductReviews() {
        return productReviews;
    }
}
