<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<jsp:useBean id="productReviews" type="java.util.ArrayList" scope="request"/>
<tags:master pageTitle="Moderator Page">
  <p>
    Welcome to Expert-Soft training!
  </p>
  <table>
    <thead>
      <tr>
        <td>Product</td>
        <td>User Name</td>
        <td>Rating</td>
        <td>Statement</td>
        <td>Moderate</td>
      </tr>
    </thead>
    <c:forEach var="productReview" items="${productReviews}">
        <c:forEach var="review" items="${productReview.reviews}">
            <tr>
                            <td>
                               <a href="products/${productReview.product.id}">
                               <img class="product-tile" src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${productReview.product.imageUrl}">
                               </a>
                             </td>
                       <td>
                         ${review.userName}
                       </td>
                       <td>
                         ${review.rating}
                       </td>
                       <td>
                         ${review.statement}
                       </td>
                       <td>
                            <button method="post" formaction="${pageContext.servletContext.contextPath}/moderateProductReview/${review.id}">Moderate</button>
                       </td>
            </tr>
        </c:forEach>
    </c:forEach>
  </table>
  <tags:recentlyViewed recentlyViewedProducts="${recentlyViewedProducts}"/>
</tags:master>