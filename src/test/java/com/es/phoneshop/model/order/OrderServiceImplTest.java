package com.es.phoneshop.model.order;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartItem;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceImplTest {
    private OrderServiceImpl orderService = OrderServiceImpl.getInstance();
    @Mock
    private OrderDao orderDao;
    @Mock
    private Cart cart;
    @Mock
    private Order order;

    private List<CartItem> cartItems = new ArrayList<>();

    @Before
    public void setup() {
        orderService.setOrderDao(orderDao);
        when(cart.getCartItems()).thenReturn(cartItems);
        when(cart.getTotalProductsPrice()).thenReturn(BigDecimal.ONE);
    }

    @Test
    public void testCreateOrder() {
        Order order = orderService.createOrder(cart);
        assertEquals(order.getTotalProductsPrice(), BigDecimal.ONE);
        assertEquals(order.getCartItems(), cartItems);
    }

    @Test
    public void testPlaceOrder() {
        orderService.placeOrder(order);
        verify(orderDao).save(order);
    }

    @Test
    public void testCalculateTotalOrderPrice() {
        when(order.getDeliveryMode()).thenReturn(DeliveryMode.COURIER);
        when(order.getTotalProductsPrice()).thenReturn(BigDecimal.ONE);
        orderService.calculateTotalOrderPrice(order);
        verify(order).setTotalOrderPrice(new BigDecimal(11));
    }
}
