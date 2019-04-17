package com.es.phoneshop.model.order;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartItem;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class OrderServiceImpl implements OrderService {
    private static final OrderServiceImpl instance = new OrderServiceImpl();
    private OrderDao orderDao;

    private OrderServiceImpl() {
        orderDao = ArrayListOrderDao.getInstance();
    }

    public static OrderServiceImpl getInstance() {
        return instance;
    }

    @Override
    public Order createOrder(Cart cart) {
        Order order = new Order();
        order.setCartItems(cart.getCartItems().stream().map(CartItem::new).collect(Collectors.toList()));
        order.setTotalProductsPrice(cart.getTotalProductsPrice());
        return order;
    }

    @Override
    public List<DeliveryMode> getDeliveryModes() {
        return Arrays.asList(DeliveryMode.values());
    }

    @Override
    public List<PaymentMethod> getPaymentMethods() {
        return Arrays.asList(PaymentMethod.values());
    }

    @Override
    public void placeOrder(Order order) {
        orderDao.save(order);
    }

    @Override
    public void calculateTotalOrderPrice(Order order) {
        BigDecimal total = order.getTotalProductsPrice().add(order.getDeliveryMode().getCost());
        order.setTotalOrderPrice(total);
    }

    @Override
    public BigDecimal getTotalOrderPrice(Cart cart, DeliveryMode deliveryMode) {
        return cart.getTotalProductsPrice().add(deliveryMode.getCost());
    }

    void setOrderDao(OrderDao orderDao) {
        this.orderDao = orderDao;
    }
}
