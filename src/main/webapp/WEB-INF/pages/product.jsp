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
</tags:master>
