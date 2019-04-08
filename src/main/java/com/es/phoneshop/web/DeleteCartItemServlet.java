package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartService;
import com.es.phoneshop.model.cart.HttpSessionCartService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteCartItemServlet extends HttpServlet {
    private static final String CART_UPDATED_SUCCESSFULLY = "?message=Cart updated successfully";

    private CartService cartService;

    @Override
    public void init(ServletConfig config) {
        cartService = HttpSessionCartService.getInstance();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long id = Long.parseLong(request.getPathInfo().substring(1));
        Cart cart = cartService.getCart(request);
        cartService.delete(cart, id);
        response.sendRedirect(request.getContextPath() + "/cart" + CART_UPDATED_SUCCESSFULLY);
    }

    void setCartService(CartService cartService) {
        this.cartService = cartService;
    }
}
