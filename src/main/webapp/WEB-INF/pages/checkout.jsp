<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<jsp:useBean id="cart" type="com.es.phoneshop.model.cart.Cart" scope="request"/>
<tags:master pageTitle="checkoutOrder">
<h1>Checkout order</h1>
        <table>
            <thead>
                <tr>
                    <td>Image</td>
                    <td>Description</td>
                    <td class="price">Price</td>
                    <td>Quantity</td>
                </tr>
            </thead>
            <c:forEach var="cartItem" items="${cart.cartItems}">
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
                <td class="price">
                    <fmt:formatNumber value="${cart.totalProductsPrice}" type="currency" currencySymbol="${not empty cart.cartItems ? cart.cartItems.get(0).product.currency.symbol : $}"/>
                </td>
                <td>
                </td>
            </tr>
        </table>
    <c:if test="${not empty emptyCartError}">
        <p style="color: red">
            ${emptyCartError}
        </p>
    </c:if>
    <form method="post" action="${pageContext.servletContext.contextPath}/checkout">
        <p>
            <label>
                First name:
                <input name="firstName" value="${param.firstName}">
            </label>
            <c:if test="${not empty firstNameError}">
                <p style="color: red">${firstNameError}</p>
            </c:if>
        </p>
        <p>
            <label>
                Last name:
                <input name="lastName" value="${param.lastName}">
            </label>
            <c:if test="${not empty lastNameError}">
                <p style="color: red">${lastNameError}</p>
            </c:if>
        </p>
        <p>
            <label>
                Address:
                <input name="address" value="${param.address}">
            </label>
            <c:if test="${not empty addressError}">
                <p style="color: red">${addressError}</p>
            </c:if>
        </p>
        <p>
            <label>
                Phone:
                <input name="phone" value="${param.phone}">
            </label>
            <c:if test="${not empty phoneError}">
                <p style="color: red">${phoneError}</p>
            </c:if>
        </p>
        <p>
            <label>
            Delivery Mode:
            <select name="deliveryMode" onchange="document.getElementById('update').click()">
                <c:forEach var="mode" items="${deliveryModes}">
                    <c:choose>
                        <c:when test="${mode == param.deliveryMode}">
                            <option selected>${mode}</option>
                        </c:when>
                        <c:otherwise>
                            <option>${mode}</option>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </select>
            </label>
            <button id="update" style="display: none" formmethod="get"></button>
        </p>
        <p>
            Delivery cost: <fmt:formatNumber value="${deliveryMode.cost}" type="currency" currencySymbol="${deliveryMode.currency.symbol}"/>
        </p>
        <p>
            <label>
            Payment method:
            <select name="paymentMethod">
                <c:forEach var="method" items="${paymentMethods}">
                    <c:choose>
                        <c:when test="${method == param.paymentMethod}">
                            <option selected>${method}</option>
                        </c:when>
                        <c:otherwise>
                            <option>${method}</option>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </select>
            </label>
        </p>
        <p>
            Total order price: <fmt:formatNumber value="${cart.totalProductsPrice + deliveryMode.cost}" type="currency" currencySymbol="${deliveryMode.currency.symbol}"/>
        </p>
        <p>
            <button>Place order</button>
        </p>
    </form>
<tags:recentlyViewed recentlyViewedProducts="${recentlyViewedProducts}"/>
</tags:master>
