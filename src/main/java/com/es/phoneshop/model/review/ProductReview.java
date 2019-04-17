package com.es.phoneshop.model.review;

import com.es.phoneshop.model.product.Product;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ProductReview implements Serializable {
    private Product product;
    private AtomicInteger atomicInteger;
    private List<Review> reviews;

    public ProductReview() {
        product = new Product();
        reviews = new ArrayList<>();
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }
}
