package com.es.phoneshop.model.order;

import com.es.phoneshop.model.cart.Cart;

import java.math.BigDecimal;
import java.util.List;

public interface OrderService {
    Order createOrder(Cart cart);

    void placeOrder(Order order);

    List<DeliveryMode> getDeliveryModes();

    List<PaymentMethod> getPaymentMethods();

    void calculateTotalOrderPrice(Order order);

    BigDecimal getTotalOrderPrice(Cart cart, DeliveryMode deliveryMode);
}
