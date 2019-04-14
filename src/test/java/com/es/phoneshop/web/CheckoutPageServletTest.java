package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartItem;
import com.es.phoneshop.model.cart.CartService;
import com.es.phoneshop.model.order.DeliveryMode;
import com.es.phoneshop.model.order.Order;
import com.es.phoneshop.model.order.OrderService;
import com.es.phoneshop.model.order.PaymentMethod;
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
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CheckoutPageServletTest {
    private CheckoutPageServlet servlet = new CheckoutPageServlet();

    @Mock
    private OrderService orderService;
    @Mock
    private CartService cartService;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private Cart cart;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    private Order order;
    @Mock
    private List<CartItem> cartItems;

    private String parameter = "parameter";

    @Before
    public void setup() {
        servlet.setCartService(cartService);
        servlet.setOrderService(orderService);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(cartService.getCart(request)).thenReturn(cart);
        when(orderService.getDeliveryModes()).thenReturn(Arrays.asList(DeliveryMode.values()));
        when(orderService.getPaymentMethods()).thenReturn(Arrays.asList(PaymentMethod.values()));
        when(request.getParameter(anyString())).thenReturn(parameter);
        when(orderService.createOrder(cart)).thenReturn(order);
        when(cart.getCartItems()).thenReturn(cartItems);
        when(request.getParameter("deliveryMode")).thenReturn(DeliveryMode.COURIER.toString());
        when(request.getParameter("paymentMethod")).thenReturn(PaymentMethod.MONEY.toString());
        when(cartItems.size()).thenReturn(1);
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        servlet.doGet(request, response);
        verify(request).setAttribute("cart", cart);
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testDoPost() throws ServletException, IOException {
        servlet.doPost(request, response);
        verify(order).setFirstName(parameter);
        verify(order).setLastName(parameter);
        verify(order).setPhone(parameter);
        verify(order).setAddress(parameter);
        verify(orderService).placeOrder(order);
        verify(cartService).clearCart(request);
        verify(response).sendRedirect(anyString());
    }

    @Test
    public void testDoPostEmptyParameter() throws ServletException, IOException {
        when(request.getParameter(anyString())).thenReturn("");
        servlet.doPost(request, response);
        verify(request, times(4)).setAttribute(anyString(), anyString());
        verify(request).setAttribute("cart", cart);
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testEmptyCart() throws ServletException, IOException {
        when(cartItems.size()).thenReturn(0);
        servlet.doPost(request, response);
        verify(request).setAttribute("emptyCartError", "To make an order you need to choose products");
    }
}
