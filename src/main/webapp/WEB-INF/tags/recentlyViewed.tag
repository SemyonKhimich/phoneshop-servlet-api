<%@ tag trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ attribute name="recentlyViewedProducts" required="true" type="java.util.List"%>
<c:if test="${not empty recentlyViewedProducts}">
<h1>Recently Viewed</h1>
<table>
    <tr>
        <c:forEach var="product" items="${recentlyViewedProducts}">
            <td>
                <a href="/phoneshop-servlet-api/products/${product.id}">
                    <img class="product-tile" src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}">
                </a><br>
                <a href="/phoneshop-servlet-api/products/${product.id}">
                    ${product.description}
                </a><br>
                <fmt:formatNumber value="${product.price}" type="currency" currencySymbol="${product.currency.symbol}"/>
            </td>
        </c:forEach>
    </tr>
</table>
</c:if>
