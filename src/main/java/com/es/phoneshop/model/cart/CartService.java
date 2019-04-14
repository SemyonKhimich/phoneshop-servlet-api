package com.es.phoneshop.model.cart;

import javax.servlet.http.HttpServletRequest;

public interface CartService {
    void add(Cart cart, Long productId, Integer quantity) throws OutOfStockException, IncorrectValueException;

    Cart getCart(HttpServletRequest request);

    void update(Cart cart, Long productId, Integer quantity) throws OutOfStockException, IncorrectValueException;

    void delete(Cart cart, Long productId);

    void calculateTotalProductsPrice(Cart cart);

    void clearCart(HttpServletRequest request);

}
