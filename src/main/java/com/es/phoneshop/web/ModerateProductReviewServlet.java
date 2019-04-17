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

public class ModerateProductReviewServlet extends HttpServlet {
    private ProductReviewDao productReviewDao;

    @Override
    public void init(ServletConfig config){
        productReviewDao = ArrayListProductReviewDao.getInstance();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        Long id = Long.parseLong(pathInfo.substring(1));
        List<ProductReview> productReviewList = productReviewDao.getProductReviews();
        productReviewList.forEach(productReview -> productReview.getReviews()
                .stream().filter(review -> review.getId().equals(id)).findAny().get().setModerated(true));
    }
}
