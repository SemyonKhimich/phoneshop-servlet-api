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

    private static final String QUERY = "query";
    private static final String ORDER = "order";
    private static final String FIELD = "field";

    @Before
    public void setup() {
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        servlet.setProductDao(productDao);
        when(productDao.findProducts(QUERY, ORDER, FIELD)).thenReturn(products);
        when(request.getParameter(FIELD)).thenReturn(FIELD);
        when(request.getParameter(ORDER)).thenReturn(ORDER);
        when(request.getParameter(QUERY)).thenReturn(QUERY);
    }

    @Test
    public void testFindProducts() throws ServletException, IOException {
        servlet.doGet(request, response);
        verify(productDao).findProducts(QUERY, ORDER, FIELD);
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
