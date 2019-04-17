package com.es.phoneshop.web;

import com.es.phoneshop.model.order.*;
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

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class OrderOverviewPageServletTest {
    private OrderOverviewPageServlet servlet = new OrderOverviewPageServlet();
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    private OrderDao orderDao;
    @Mock
    private Order order;

    @Before
    public void setup() {
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(orderDao.getBySecureId(anyString())).thenReturn(order);
        when(request.getPathInfo()).thenReturn("pathInfo");
        servlet.setOrderDao(orderDao);
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        servlet.doGet(request, response);
        verify(request).setAttribute("order", order);
        verify(requestDispatcher).forward(request, response);
    }
}
