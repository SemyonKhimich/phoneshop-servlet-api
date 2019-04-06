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
public class MiniCartServletTest {
    @Mock
    private HttpServletResponse response;
    @Mock
    private HttpServletRequest request;
    @Mock
    private CartService cartService;
    @Mock
    private Cart cart;
    @Mock
    private RequestDispatcher requestDispatcher;

    private MiniCartServlet miniCartServlet = new MiniCartServlet();

    @Before
    public void setup() {
        miniCartServlet.setCartService(cartService);
        when(cartService.getCart(request)).thenReturn(cart);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        miniCartServlet.doGet(request, response);
        verify(request).setAttribute("cart", cart);
        verify(requestDispatcher).include(request, response);
    }
}
