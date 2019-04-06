package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CartPageServletTest {
    @Mock
    private Cart cart;
    @Mock
    private CartService cartService;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher requestDispatcher;

    private CartPageServlet cartPageServlet = new CartPageServlet();

    private String[] quantities = new String[1];
    private String[] productIds = new String[1];
    private static final Long ID = 1L;
    private static final Integer QUANTITY = 1;

    @Before
    public void setup() {
        when(request.getParameterValues("quantity")).thenReturn(quantities);
        when(request.getParameterValues("productId")).thenReturn(productIds);
        productIds[0] = ID.toString();
        cartPageServlet.setCartService(cartService);
        when(cartService.getCart(request)).thenReturn(cart);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        cartPageServlet.doGet(request, response);
        verify(request).setAttribute("cart", cart);
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testDoPostNullProductIds() throws ServletException, IOException {
        when(request.getParameterValues("quantity")).thenReturn(null);
        when(request.getParameterValues("productId")).thenReturn(null);
        cartPageServlet.doPost(request, response);
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testDoPostUpdateCart() throws ServletException, IOException, OutOfStockException, IncorrectValueException {
        quantities[0] = QUANTITY.toString();
        cartPageServlet.doPost(request, response);
        verify(cartService).update(cart, ID, QUANTITY);
        verify(response).sendRedirect(anyString());
    }

    @Test
    public void testDoPostError() throws ServletException, IOException {
        quantities[0] = "dsf";
        cartPageServlet.doPost(request, response);
        verify(request).setAttribute(eq("errors"), any(quantities.getClass()));
    }

    @Test
    public void testDoPost() throws ServletException, IOException, OutOfStockException, IncorrectValueException {
        quantities[0] = QUANTITY.toString();
        doThrow(new IncorrectValueException("mes")).when(cartService).update(cart, ID, QUANTITY);
        cartPageServlet.doPost(request, response);
        verify(request).setAttribute(eq("errors"), any(quantities.getClass()));
    }
}
