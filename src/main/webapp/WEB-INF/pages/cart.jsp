<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<jsp:useBean id="cart" type="com.es.phoneshop.model.cart.Cart" scope="request"/>
<tags:master pageTitle="Cart">
<h1>Cart</h1>
<c:choose>
    <c:when test="${not empty errors}">
        <p style="color: red">
            Error updating cart
        </p>
    </c:when>
    <c:otherwise>
        <p style="color: green">
            ${param.message}
        </p>
    </c:otherwise>
</c:choose>
<form method="post">
    <table>
        <thead>
            <tr>
                <td>Image</td>
                <td>Description</td>
                <td class="price">Price</td>
                <td>Quantity</td>
                <td>Delete</td>
            </tr>
        </thead>
        <c:forEach var="cartItem" items="${cart.cartItems}" varStatus="counter">
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
                    <input name="quantity" value="${not empty paramValues.quantity[counter.index] ? paramValues.quantity[counter.index] : cartItem.quantity}" style="text-align: right">
                    <input type="hidden" name="productId" value="${cartItem.product.id}">
                    <c:if test="${not empty errors[counter.index]}">
                        <p style="color: red">${errors[counter.index]}</p>
                    </c:if>
                </td>
                <td>
                    <button method="post" formaction="${pageContext.servletContext.contextPath}/cart/deleteCartItem/${cartItem.product.id}">Delete</button>
                </td>
            </tr>
        </c:forEach>
    </table>
    <p>
        <button>Update</button>
    </p>
</form>
<tags:recentlyViewed recentlyViewedProducts="${recentlyViewedProducts}"/>
</tags:master>
