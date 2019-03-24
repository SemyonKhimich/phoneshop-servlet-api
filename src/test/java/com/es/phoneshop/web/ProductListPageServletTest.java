package com.es.phoneshop.web;

import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ProductListPageServletTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    private ProductDao productDao;
    @Mock
    private List<Product> products;

    private ProductListPageServlet servlet = new ProductListPageServlet();

    private String query = "query";
    private String order = "order";
    private String field = "field";

    @Before
    public void setup() {
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        servlet.setProductDao(productDao);
        when(productDao.findProducts(query, order, field)).thenReturn(products);
        when(request.getParameter(ProductListPageServlet.FIELD)).thenReturn(field);
        when(request.getParameter(ProductListPageServlet.ORDER)).thenReturn(order);
        when(request.getParameter(ProductListPageServlet.QUERY)).thenReturn(query);
    }

    @Test
    public void testFindProducts() throws ServletException, IOException {
        servlet.doGet(request, response);
        verify(productDao).findProducts(query, order, field);
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        servlet.doGet(request, response);
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testDoGetSetAttribute() throws ServletException, IOException {
        servlet.doGet(request, response);
        verify(request).setAttribute("products", products);
    }
}
