package com.es.phoneshop.web;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.ProductDao;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProductListPageServlet extends HttpServlet {
    private ProductDao productDao;
    private static final String QUERY = "query";
    private static final String ORDER = "order";
    private static final String FIELD = "field";

    @Override
    public void init(ServletConfig config) {
        productDao = ArrayListProductDao.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String queryParam = request.getParameter(QUERY);
        String orderParam = request.getParameter(ORDER);
        String fieldParam = request.getParameter(FIELD);
        request.setAttribute("products", productDao.findProducts(queryParam, orderParam, fieldParam));
        request.getRequestDispatcher("/WEB-INF/pages/productList.jsp").forward(request, response);
    }

    void setProductDao(ProductDao productDao) {
        this.productDao = productDao;
    }


}
