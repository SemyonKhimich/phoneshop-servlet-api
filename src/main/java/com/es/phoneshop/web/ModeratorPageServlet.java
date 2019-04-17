package com.es.phoneshop.web;

import com.es.phoneshop.model.review.ArrayListProductReviewDao;
import com.es.phoneshop.model.review.ProductReview;
import com.es.phoneshop.model.review.ProductReviewDao;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ModeratorPageServlet extends HttpServlet {
    private ProductReviewDao productReviewDao;

    @Override
    public void init(ServletConfig config) {
        productReviewDao = ArrayListProductReviewDao.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<ProductReview> productReviewList = productReviewDao.getProductReviews();
        request.setAttribute("productReviews", productReviewList);
        request.getRequestDispatcher("/WEB-INF/pages/moderatorPage.jsp").forward(request, response);
    }
}
