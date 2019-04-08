package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DeleteCartItemServletTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private CartService cartService;
    @Mock
    private Cart cart;

    private static final String pathInfo = "/10";
    private static final Long ID = 10L;
    private DeleteCartItemServlet deleteCartItemServlet = new DeleteCartItemServlet();

    @Before
    public void setup() {
        when(request.getPathInfo()).thenReturn(pathInfo);
        deleteCartItemServlet.setCartService(cartService);
        when(cartService.getCart(request)).thenReturn(cart);
    }

    @Test
    public void testDoPost() throws ServletException, IOException {
        deleteCartItemServlet.doPost(request, response);
        verify(cartService).delete(cart, ID);
        verify(response).sendRedirect(anyString());
    }
}
