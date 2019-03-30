package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.HttpSessionCartService;
import com.es.phoneshop.model.cart.OutOfStockException;
import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.ProductDao;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProductDetailsPageServlet extends HttpServlet {
    private ProductDao productDao;
    protected static final String ERROR = "error";
    protected static final String NOT_A_POSITIVE_NUMBER = "not a positive number";
    protected static final String QUANTITY = "quantity";
    protected static final String ADDED_TO_CART_SUCCESSFULLY = "?message=Added to cart successfully";

    @Override
    public void init(ServletConfig config) throws ServletException {
        productDao = ArrayListProductDao.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        Long id = Long.parseLong(pathInfo.substring(1));
        request.setAttribute("product", productDao.getProduct(id));
        request.getRequestDispatcher("/WEB-INF/pages/product.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        Long id = Long.parseLong(pathInfo.substring(1));
        Integer quantity;
        Cart cart = HttpSessionCartService.getInstance().getCart(request);
        try {
            quantity = Integer.valueOf(request.getParameter(QUANTITY));
            if (quantity <= 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException exc) {
            request.setAttribute(ERROR, NOT_A_POSITIVE_NUMBER);
            doGet(request, response);
            return;
        }
        try {
            HttpSessionCartService.getInstance().add(cart, id, quantity);
        } catch (OutOfStockException exc) {
            request.setAttribute(ERROR, exc.getMessage());
            doGet(request, response);
            return;
        }
        response.sendRedirect(request.getRequestURI() + ADDED_TO_CART_SUCCESSFULLY);
    }

    public void setProductDao(ProductDao productDao) {
        this.productDao = productDao;
    }
}
