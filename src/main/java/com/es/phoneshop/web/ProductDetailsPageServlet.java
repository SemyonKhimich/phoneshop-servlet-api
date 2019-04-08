package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.*;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.recently.viewed.HttpSessionRecentlyViewedProductsService;
import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.ProductDao;
import com.es.phoneshop.model.recently.viewed.RecentlyViewedProductsService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ProductDetailsPageServlet extends HttpServlet {
    private ProductDao productDao;
    private CartService cartService;
    private RecentlyViewedProductsService recentlyViewedProductsService;
    private static final String ERROR = "error";
    private static final String NOT_A_NUMBER = "not a number";
    private static final String QUANTITY = "quantity";
    private static final String ADDED_TO_CART_SUCCESSFULLY = "?message=Added to cart successfully";

    @Override
    public void init(ServletConfig config) {
        productDao = ArrayListProductDao.getInstance();
        cartService = HttpSessionCartService.getInstance();
        recentlyViewedProductsService = HttpSessionRecentlyViewedProductsService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        Long id = Long.parseLong(pathInfo.substring(1));
        List<Product> products = recentlyViewedProductsService.getRecentlyViewedProducts(request);
        recentlyViewedProductsService.add(products, id);
        request.setAttribute("product", productDao.getProduct(id));
        request.getRequestDispatcher("/WEB-INF/pages/product.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        Long id = Long.parseLong(pathInfo.substring(1));
        Integer quantity;
        Cart cart = cartService.getCart(request);
        try {
            quantity = Integer.valueOf(request.getParameter(QUANTITY));
        } catch (NumberFormatException exc) {
            request.setAttribute(ERROR, NOT_A_NUMBER);
            doGet(request, response);
            return;
        }
        try {
            cartService.add(cart, id, quantity);
        } catch (OutOfStockException | IncorrectValueException exc) {
            request.setAttribute(ERROR, exc.getMessage());
            doGet(request, response);
            return;
        }
        response.sendRedirect(request.getRequestURI() + ADDED_TO_CART_SUCCESSFULLY);
    }

    void setProductDao(ProductDao productDao) {
        this.productDao = productDao;
    }

    void setCartService(CartService cartService) {
        this.cartService = cartService;
    }

    void setRecentlyViewedProductsService(RecentlyViewedProductsService recentlyViewedProductsService) {
        this.recentlyViewedProductsService = recentlyViewedProductsService;
    }
}
