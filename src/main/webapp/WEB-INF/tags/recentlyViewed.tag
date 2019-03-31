<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="recentlyViewedProducts" required="true" type="com.es.phoneshop.model.recently.viewed.RecentlyViewedProducts"%>
<c:if test="${not empty recentlyViewedProducts}">
<h1>Recently Viewed</h1>
<table>
    <tr>
        <c:forEach var="product" items="${recentlyViewedProducts.products}">
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
