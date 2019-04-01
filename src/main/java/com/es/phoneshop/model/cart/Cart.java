package com.es.phoneshop.model.cart;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Cart implements Serializable {
    private final List<CartItem> cartItems;

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public Cart() {
        cartItems = new ArrayList<>();
    }
}
