package com.es.phoneshop.model.order;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ArrayListOrderDao implements OrderDao {
    private static final ArrayListOrderDao instance = new ArrayListOrderDao();
    private List<Order> orders;
    private AtomicLong orderId;

    private ArrayListOrderDao() {
        orders = new ArrayList<>();
        orderId = new AtomicLong();
    }

    public static ArrayListOrderDao getInstance() {
        return instance;
    }

    @Override
    public void save(Order order) {
        order.setId(orderId.incrementAndGet());
        order.setSecureId(UUID.randomUUID().toString());
        orders.add(order);
    }

    @Override
    public Order getBySecureId(String secureId) {
        return orders.stream()
                .filter(order -> secureId.equals(order.getSecureId()))
                .findAny().orElseThrow(() -> new OrderNotFoundException("Order not found"));
    }

    void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
