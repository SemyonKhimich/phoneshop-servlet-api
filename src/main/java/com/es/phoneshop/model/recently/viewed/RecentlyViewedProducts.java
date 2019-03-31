package com.es.phoneshop.model.recently.viewed;

import com.es.phoneshop.model.product.Product;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class RecentlyViewedProducts implements Serializable {
    private final List<Product> products;

    public RecentlyViewedProducts() {
        products = new LinkedList<>();
    }

    public List<Product> getProducts() {
        return products;
    }
}
