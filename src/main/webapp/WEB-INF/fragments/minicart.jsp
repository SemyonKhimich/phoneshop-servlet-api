<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<jsp:useBean id="cart" type="com.es.phoneshop.model.cart.Cart" scope="request"/>
<h1>
    <a href="${pageContext.servletContext.contextPath}/cart">
        My Cart
    </a>
</h1>
<c:if test="${not empty cart.cartItems}">
    <table>
        <thead>
            <tr>
                <td>Product</td>
                <td>Quantity</td>
            </tr>
        </thead>
        <c:forEach var="cartItem" items="${cart.cartItems}">
            <tr>
                <td>
                    <a href="${pageContext.servletContext.contextPath}/products/${cartItem.product.id}">
                        <img class="product-tile" src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${cartItem.product.imageUrl}">
                    <br>
                        ${cartItem.product.description}
                    <br>
                    <fmt:formatNumber value="${cartItem.product.price}" type="currency" currencySymbol="${cartItem.product.currency.symbol}"/>
                    </a>
                </td>
                <td>
                    ${cartItem.quantity}
                </td>
            </tr>
        </c:forEach>
    </table>
</c:if>
