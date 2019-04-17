<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<jsp:useBean id="product" type="com.es.phoneshop.model.product.Product" scope="request"/>
<tags:master pageTitle="Product">
  <table>
    <thead>
        <tr>
          <td>Image</td>
          <td>Code</td>
          <td>Description</td>
          <td class="price">Price</td>
          <td>Stock</td>
        </tr>
    </thead>
    <tr>
        <td>
            <img class="product-tile" src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}">
        </td>
        <td>${product.code}</td>
        <td>${product.description}</td>
        <td class="price">
            <fmt:formatNumber value="${product.price}" type="currency" currencySymbol="${product.currency.symbol}"/>
        </td>
        <td>${product.stock}</td>
    </tr>
  </table>
<p>
    <form method="post">
        <input name="quantity" value="${not empty param.quantity ? param.quantity : 1}" style="text-align: right">
        <button>Add product</button>
    </form>
</p>
<c:choose>
    <c:when test="${not empty error}">
        <p style="color: red">
            ${error}
        </p>
    </c:when>
    <c:otherwise>
        <p style="color: green">
            ${param.message}
        </p>
    </c:otherwise>
</c:choose>
<h1>
    Add review
</h1>
<p>${param.result}</p>
<p>
    <form method="post" action="${pageContext.servletContext.contextPath}/addReview/${product.id}">
        <p>
            userName: <input name="userName" required>
        </p>
        <p>
            Rating: <input name="rating" required>
        </p>
        <p>
            Statement: <input name="statement" required>
        </p>
        <button>Add review</button>
    </form>
</p>
<jsp:useBean id="productReview" type="com.es.phoneshop.model.review.ProductReview" scope="request"/>
<table>
    <thead>
            <tr>
              <td>User Name</td>
              <td>Rating</td>
              <td>Statement</td>
            </tr>
        </thead>
        <c:forEach var="review" items="${productReview.reviews}">
              <tr>
                <td>
                  ${review.userName}
                </td>
                <td>
                  ${review.rating}
                </td>
                <td>
                  ${review.statement}
                </td>
              </tr>
        </c:forEach>
</table>
<jsp:include page="/cart/minicart"/>
<tags:recentlyViewed recentlyViewedProducts="${recentlyViewedProducts}"/>
</tags:master>
