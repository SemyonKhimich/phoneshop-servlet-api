package com.es.phoneshop.model.cart;

import javax.servlet.http.HttpServletRequest;

public interface CartService {
    void add(Cart cart, Long productId, Integer quantity) throws OutOfStockException;

    Cart getCart(HttpServletRequest request);

}
