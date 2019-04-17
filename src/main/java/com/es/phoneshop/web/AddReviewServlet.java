package com.es.phoneshop.web;

import com.es.phoneshop.model.review.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddReviewServlet extends HttpServlet {
    private ProductReviewService productReviewService;
    private ProductReviewDao productReviewDao;

    @Override
    public void init(ServletConfig config) {
        productReviewDao = ArrayListProductReviewDao.getInstance();
        productReviewService = ProductReviewServiceImpl.getInstance();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        Long id = Long.parseLong(pathInfo.substring(1));
        String userName = request.getParameter("userName");
        Integer rating;
        try {
            rating = Integer.valueOf(request.getParameter("rating"));
        } catch (NumberFormatException exc) {
            response.sendRedirect(request.getContextPath() + "/products" + pathInfo + "?result=" + "rating must be a number");
            return;
        }
        String statement = request.getParameter("statement");
        Review review = new Review(userName, rating, statement);
        ProductReview productReview = productReviewDao.getProductReview(id);
        try {
            productReviewService.addReview(productReview, review);
        } catch (IncorrectRatingException exc) {
            response.sendRedirect(request.getContextPath() + "/products" + pathInfo + "?result=" + exc.getMessage());
            return;
        }
        response.sendRedirect(request.getContextPath() + "/products" + pathInfo + "?result=review go to moderate");
    }
}
