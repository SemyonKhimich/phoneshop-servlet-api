package com.es.phoneshop.model.recently.viewed;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;

import javax.servlet.http.HttpServletRequest;

public class HttpSessionRecentlyViewedProductsService implements RecentlyViewedProductsService {
    static String RECENTLY_VIEWED_PRODUCTS = "recentlyViewedProducts";
    private static HttpSessionRecentlyViewedProductsService instance = new HttpSessionRecentlyViewedProductsService();
    static final Integer NUMBER_OF_RECENTLY_VIEWED_PRODUCTS = 3;

    public static HttpSessionRecentlyViewedProductsService getInstance() {
        return instance;
    }


    public static void setInstance(HttpSessionRecentlyViewedProductsService instance) {
        HttpSessionRecentlyViewedProductsService.instance = instance;
    }

    @Override
    public void add(RecentlyViewedProducts recentlyViewedProducts, Long id) {
        Product product = ArrayListProductDao.getInstance().getProduct(id);
        if (!recentlyViewedProducts.getProducts().contains(product)) {
            if (recentlyViewedProducts.getProducts().size() >= NUMBER_OF_RECENTLY_VIEWED_PRODUCTS) {
                recentlyViewedProducts.getProducts().remove(0);
            }
            recentlyViewedProducts.getProducts().add(product);
        }
    }

    @Override
    public RecentlyViewedProducts getRecentlyViewedProducts(HttpServletRequest request) {
        RecentlyViewedProducts products = (RecentlyViewedProducts) request.getSession().getAttribute(RECENTLY_VIEWED_PRODUCTS);
        if (products == null) {
            products = new RecentlyViewedProducts();
            request.getSession().setAttribute(RECENTLY_VIEWED_PRODUCTS, products);
        }
        return products;
    }
}
