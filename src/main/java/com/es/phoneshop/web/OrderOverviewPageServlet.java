package com.es.phoneshop.web;

import com.es.phoneshop.model.order.ArrayListOrderDao;
import com.es.phoneshop.model.order.Order;
import com.es.phoneshop.model.order.OrderDao;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class OrderOverviewPageServlet extends HttpServlet {
    private OrderDao orderDao;

    @Override
    public void init(ServletConfig config) {
        orderDao = ArrayListOrderDao.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Order order = orderDao.getBySecureId(request.getPathInfo().substring(1));
        request.setAttribute("order", order);
        request.getRequestDispatcher("/WEB-INF/pages/orderOverview.jsp").forward(request, response);
    }

    void setOrderDao(OrderDao orderDao) {
        this.orderDao = orderDao;
    }
}
