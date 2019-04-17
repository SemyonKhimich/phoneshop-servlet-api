package com.es.phoneshop.model.cart;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Cart implements Serializable {
    private List<CartItem> cartItems;

    private BigDecimal totalProductsPrice;

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public BigDecimal getTotalProductsPrice() {
        return totalProductsPrice;
    }

    public void setTotalProductsPrice(BigDecimal totalProductsPrice) {
        this.totalProductsPrice = totalProductsPrice;
    }

    public Cart() {
        cartItems = new ArrayList<>();
    }
}
