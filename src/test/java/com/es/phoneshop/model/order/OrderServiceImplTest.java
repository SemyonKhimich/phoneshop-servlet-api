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

    private DeliveryMode deliveryMode = DeliveryMode.COURIER;

    private BigDecimal bigDecimal = BigDecimal.ONE;

    @Before
    public void setup() {
        orderService.setOrderDao(orderDao);
        when(cart.getCartItems()).thenReturn(cartItems);
        when(cart.getTotalProductsPrice()).thenReturn(bigDecimal);
    }

    @Test
    public void testCreateOrder() {
        Order order = orderService.createOrder(cart);
        assertEquals(order.getTotalProductsPrice(), bigDecimal);
        assertEquals(order.getCartItems(), cartItems);
    }

    @Test
    public void testPlaceOrder() {
        orderService.placeOrder(order);
        verify(orderDao).save(order);
    }

    @Test
    public void testCalculateTotalOrderPrice() {
        when(order.getDeliveryMode()).thenReturn(deliveryMode);
        when(order.getTotalProductsPrice()).thenReturn(bigDecimal);
        orderService.calculateTotalOrderPrice(order);
        verify(order).setTotalOrderPrice(new BigDecimal(11));
    }

    @Test
    public void testGetTotalOrderPrice() {
        BigDecimal totalOrderPrice = orderService.getTotalOrderPrice(cart, deliveryMode);
        assertEquals(totalOrderPrice, deliveryMode.getCost().add(bigDecimal));
    }
}
