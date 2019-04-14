<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<jsp:useBean id="order" type="com.es.phoneshop.model.order.Order" scope="request"/>
<tags:master pageTitle="orderOverView">
<h1>Order</h1>
        <table>
            <thead>
                <tr>
                    <td>Image</td>
                    <td>Description</td>
                    <td class="price">Price</td>
                    <td>Quantity</td>
                </tr>
            </thead>
            <c:forEach var="cartItem" items="${order.cartItems}">
                <tr>
                    <td>
                        <a href="products/${cartItem.product.id}">
                            <img class="product-tile" src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${cartItem.product.imageUrl}">
                        </a>
                    </td>
                    <td><a href="products/${cartItem.product.id}">
                        ${cartItem.product.description}</a></td>
                    <td class="price">
                        <fmt:formatNumber value="${cartItem.product.price}" type="currency" currencySymbol="${cartItem.product.currency.symbol}"/>
                    </td>
                    <td>
                        ${cartItem.quantity}
                    </td>
                </tr>
            </c:forEach>
            <tr>
                <td colspan="2" style="text-align: right">
                    Total
                </td>
                ${order.totalProductsPrice}
                <td class="price">
                    <fmt:formatNumber value="${order.totalProductsPrice}" type="currency" currencySymbol="${not empty order.cartItems ? order.cartItems.get(0).product.currency.symbol : $}"/>
                </td>
                <td>
                </td>
            </tr>
        </table>
        <p>
            First name: ${order.firstName}
        </p>
        <p>
            Last name: ${order.lastName}
        </p>
        <p>
            Address: ${order.address}
        </p>
        <p>
            Phone: ${order.phone}
        </p>
        <p>
            Delivery Mode: ${order.deliveryMode}
        </p>
        <p>
           Delivery cost: <fmt:formatNumber value="${order.deliveryMode.cost}" type="currency" currencySymbol="${order.deliveryMode.currency.symbol}"/>
        </p>
        <p>
            Payment method: ${order.paymentMethod}
        </p>
        <p>
            Total order price: <fmt:formatNumber value="${order.totalOrderPrice}" type="currency" currencySymbol="${order.deliveryMode.currency.symbol}"/>
        </p>
<tags:recentlyViewed recentlyViewedProducts="${recentlyViewedProducts}"/>
</tags:master>
