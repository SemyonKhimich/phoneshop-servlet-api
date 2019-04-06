package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

public class CartPageServlet extends HttpServlet {
    private CartService cartService;
    private static final String NOT_A_NUMBER = "not a number";
    private static final String CART_UPDATED_SUCCESSFULLY = "?message=Cart updated successfully";


    @Override
    public void init(ServletConfig config) {
        cartService = HttpSessionCartService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("cart", cartService.getCart(request));
        request.getRequestDispatcher("/WEB-INF/pages/cart.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] quantities = request.getParameterValues("quantity");
        String[] productIds = request.getParameterValues("productId");
        if (productIds == null) {
            doGet(request, response);
            return;
        }
        String[] errors = new String[productIds.length];
        Cart cart = cartService.getCart(request);
        for (int i = 0; i < productIds.length; i++) {
            Long productId = Long.valueOf(productIds[i]);
            Integer quantity = null;
            try {
                quantity = Integer.valueOf(quantities[i]);
            } catch (NumberFormatException exc) {
                errors[i] = NOT_A_NUMBER;
            }
            if (quantity != null) {
                try {
                    cartService.update(cart, productId, quantity);
                } catch (OutOfStockException | IncorrectValueException exc) {
                    errors[i] = exc.getMessage();
                }
            }
        }
        if (Arrays.stream(errors).anyMatch(Objects::nonNull)) {
            request.setAttribute("errors", errors);
            doGet(request, response);
            return;
        }
        response.sendRedirect(request.getRequestURI() + CART_UPDATED_SUCCESSFULLY);
    }

    void setCartService(CartService cartService) {
        this.cartService = cartService;
    }
}
