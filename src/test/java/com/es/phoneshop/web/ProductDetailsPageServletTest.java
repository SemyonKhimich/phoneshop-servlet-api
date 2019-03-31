package com.es.phoneshop.web;

import com.es.phoneshop.model.recently.viewed.HttpSessionRecentlyViewedProductsService;
import com.es.phoneshop.model.recently.viewed.RecentlyViewedProducts;
import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.HttpSessionCartService;
import com.es.phoneshop.model.cart.OutOfStockException;
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

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ProductDetailsPageServletTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    private ProductDao productDao;

    private String pathInfo = "/10";
    private static final Long ID = 10L;
    private static final Integer QUANTITY_INT = 1;
    private static final String MESSAGE = "message";

    @Mock
    private Product product;
    @Mock
    private HttpSessionCartService httpSessionCartService;
    @Mock
    private Cart cart;
    @Mock
    private HttpSessionRecentlyViewedProductsService recentlyViewedProductsService;
    @Mock
    private RecentlyViewedProducts recentlyViewedProducts;

    private ProductDetailsPageServlet servlet = new ProductDetailsPageServlet();

    @Before
    public void setup() {
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        servlet.setProductDao(productDao);
        when(request.getPathInfo()).thenReturn(pathInfo);
        when(productDao.getProduct(ID)).thenReturn(product);
        HttpSessionCartService.setInstance(httpSessionCartService);
        when(httpSessionCartService.getCart(request)).thenReturn(cart);
        HttpSessionRecentlyViewedProductsService.setInstance(recentlyViewedProductsService);
        when(recentlyViewedProductsService.getRecentlyViewedProducts(request)).thenReturn(recentlyViewedProducts);
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        servlet.doGet(request, response);
        verify(recentlyViewedProductsService).add(recentlyViewedProducts, ID);
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testSetAttribute() throws ServletException, IOException {
        servlet.doGet(request, response);
        verify(request).setAttribute("product", product);
    }

    @Test
    public void testDoPostNumberFormatException() throws ServletException, IOException {
        servlet.doPost(request, response);
        verify(request).setAttribute(ProductDetailsPageServlet.ERROR, ProductDetailsPageServlet.NOT_A_POSITIVE_NUMBER);
    }

    @Test
    public void testDoPostNumberFormatException2() throws ServletException, IOException {
        when(request.getParameter(ProductDetailsPageServlet.QUANTITY)).thenReturn("-5");
        servlet.doPost(request, response);
        verify(request).setAttribute(ProductDetailsPageServlet.ERROR, ProductDetailsPageServlet.NOT_A_POSITIVE_NUMBER);
    }

    @Test
    public void testDoPostOutOfStockException() throws ServletException, IOException, OutOfStockException {
        when(request.getParameter(ProductDetailsPageServlet.QUANTITY)).thenReturn(QUANTITY_INT.toString());
        doThrow(new OutOfStockException(MESSAGE)).when(httpSessionCartService).add(cart, ID, QUANTITY_INT);
        servlet.doPost(request, response);
        verify(request).setAttribute(ProductDetailsPageServlet.ERROR, MESSAGE);
    }


    @Test
    public void testDoPostSendRedirect() throws ServletException, IOException {
        when(request.getParameter(ProductDetailsPageServlet.QUANTITY)).thenReturn(QUANTITY_INT.toString());
        servlet.doPost(request, response);
        verify(response).sendRedirect(anyString());
    }
}
