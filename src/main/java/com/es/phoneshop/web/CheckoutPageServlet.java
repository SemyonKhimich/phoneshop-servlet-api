package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartService;
import com.es.phoneshop.model.cart.HttpSessionCartService;
import com.es.phoneshop.model.order.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CheckoutPageServlet extends HttpServlet {
    private CartService cartService;
    private OrderService orderService;

    @Override
    public void init(ServletConfig config) {
        orderService = OrderServiceImpl.getInstance();
        cartService = HttpSessionCartService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        renderCheckoutPage(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String firstName = getParameterValueFromRequest(request, "firstName");
        String lastName = getParameterValueFromRequest(request, "lastName");
        String address = getParameterValueFromRequest(request, "address");
        String phone = getParameterValueFromRequest(request, "phone");
        if (firstName == null || lastName == null || phone == null || address == null) {
            renderCheckoutPage(request, response);
        } else {
            Cart cart = cartService.getCart(request);
            if (cart.getCartItems().size() == 0) {
                request.setAttribute("emptyCartError", "To make an order you need to choose products");
                renderCheckoutPage(request, response);
                return;
            }
            Order order = orderService.createOrder(cart);
            order.setAddress(address);
            order.setPhone(phone);
            order.setFirstName(firstName);
            order.setLastName(lastName);
            String deliveryModeString = request.getParameter("deliveryMode");
            String paymentMethodString = request.getParameter("paymentMethod");
            order.setDeliveryMode(DeliveryMode.valueOf(deliveryModeString));
            order.setPaymentMethod(PaymentMethod.valueOf(paymentMethodString));
            orderService.calculateTotalOrderPrice(order);
            orderService.placeOrder(order);
            cartService.clearCart(request);
            response.sendRedirect(request.getContextPath() + "/orderOverview/" + order.getSecureId());
        }
    }

    private void renderCheckoutPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cart cart = cartService.getCart(request);
        request.setAttribute("cart", cart);
        String deliveryModeString = request.getParameter("deliveryMode");
        if (deliveryModeString == null) {
            deliveryModeString = DeliveryMode.values()[0].toString();
        }
        DeliveryMode deliveryMode = DeliveryMode.valueOf(deliveryModeString);
        request.setAttribute("deliveryMode", deliveryMode);
        request.setAttribute("deliveryModes", orderService.getDeliveryModes());
        request.setAttribute("paymentMethods", orderService.getPaymentMethods());
        request.getRequestDispatcher("/WEB-INF/pages/checkout.jsp").forward(request, response);
    }

    private String getParameterValueFromRequest(HttpServletRequest request, String parameter) {
        String value = request.getParameter(parameter);
        if (value == null || value.isEmpty()) {
            request.setAttribute(parameter + "Error", parameter + " is required");
            return null;
        }
        return value;
    }

    void setCartService(CartService cartService) {
        this.cartService = cartService;
    }

    void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }
}
