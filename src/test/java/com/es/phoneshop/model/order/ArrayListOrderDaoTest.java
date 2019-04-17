package com.es.phoneshop.model.order;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class ArrayListOrderDaoTest {
    private ArrayListOrderDao orderDao = ArrayListOrderDao.getInstance();
    @Mock
    private Order order;

    private List<Order> orders;

    private static final String SECURE_ID = "secureId";

    @Before
    public void setup() {
        orders = new ArrayList<>();
        when(order.getSecureId()).thenReturn(SECURE_ID);
    }

    @Test
    public void testSave() {
        orderDao.setOrders(orders);
        orderDao.save(order);
        verify(order).setId(anyLong());
        verify(order).setSecureId(anyString());
        assertEquals(orders.size(), 1);
    }

    @Test
    public void testGetBySecureId() throws OrderNotFoundException {
        orders.add(order);
        orderDao.setOrders(orders);
        assertEquals(order, orderDao.getBySecureId(SECURE_ID));
    }

    @Test(expected = OrderNotFoundException.class)
    public void testGetBySecureIdException() throws OrderNotFoundException {
        orderDao.setOrders(orders);
        orderDao.getBySecureId(SECURE_ID);
    }
}
