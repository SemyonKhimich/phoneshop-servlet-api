package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.*;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;
import com.es.phoneshop.model.recently.viewed.RecentlyViewedProductsService;
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
public class ProductDetailsPageServletTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    private ProductDao productDao;

    private static final String pathInfo = "/10";
    private static final Long ID = 10L;
    private static final Integer QUANTITY_INT = 1;
    private static final String MESSAGE = "message";
    private static final String ERROR = "error";
    private static final String NOT_A_NUMBER = "not a number";
    private static final String QUANTITY = "quantity";

    @Mock
    private Product product;
    @Mock
    private CartService cartService;
    @Mock
    private Cart cart;
    @Mock
    private List<Product> products;
    @Mock
    private RecentlyViewedProductsService recentlyViewedProductsService;

    private ProductDetailsPageServlet servlet = new ProductDetailsPageServlet();

    @Before
    public void setup() {
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        servlet.setProductDao(productDao);
        when(request.getPathInfo()).thenReturn(pathInfo);
        when(productDao.getProduct(ID)).thenReturn(product);
        servlet.setCartService(cartService);
        when(cartService.getCart(request)).thenReturn(cart);
        servlet.setRecentlyViewedProductsService(recentlyViewedProductsService);
        when(recentlyViewedProductsService.getRecentlyViewedProducts(request)).thenReturn(products);
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        servlet.doGet(request, response);
        verify(recentlyViewedProductsService).add(products, ID);
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
        verify(request).setAttribute(ERROR, NOT_A_NUMBER);
    }

    @Test
    public void testDoPostOutOfStockException() throws ServletException, IOException, OutOfStockException, IncorrectValueException {
        when(request.getParameter(QUANTITY)).thenReturn(QUANTITY_INT.toString());
        doThrow(new OutOfStockException(MESSAGE)).when(cartService).add(cart, ID, QUANTITY_INT);
        servlet.doPost(request, response);
        verify(request).setAttribute(ERROR, MESSAGE);
    }

    @Test
    public void testDoPostIncorrectValueException() throws ServletException, IOException, IncorrectValueException, OutOfStockException, IncorrectValueException {
        when(request.getParameter(QUANTITY)).thenReturn("0");
        doThrow(new IncorrectValueException(MESSAGE)).when(cartService).add(cart, ID, 0);
        servlet.doPost(request, response);
        verify(request).setAttribute(ERROR, MESSAGE);
    }


    @Test
    public void testDoPostSendRedirect() throws ServletException, IOException {
        when(request.getParameter(QUANTITY)).thenReturn(QUANTITY_INT.toString());
        servlet.doPost(request, response);
        verify(response).sendRedirect(anyString());
    }
}
