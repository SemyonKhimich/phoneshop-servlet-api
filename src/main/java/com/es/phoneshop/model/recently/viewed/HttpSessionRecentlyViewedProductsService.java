package com.es.phoneshop.model.recently.viewed;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedList;
import java.util.List;

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
    public void add(List<Product> recentlyViewedProducts, Long id) {
        Product product = ArrayListProductDao.getInstance().getProduct(id);
        if (!recentlyViewedProducts.contains(product)) {
            if (recentlyViewedProducts.size() >= NUMBER_OF_RECENTLY_VIEWED_PRODUCTS) {
                recentlyViewedProducts.remove(0);
            }
            recentlyViewedProducts.add(product);
        } else {
            recentlyViewedProducts.remove(product);
            recentlyViewedProducts.add(product);
        }
    }

    @Override
    public List<Product> getRecentlyViewedProducts(HttpServletRequest request) {
        List<Product> products = (List<Product>) request.getSession().getAttribute(RECENTLY_VIEWED_PRODUCTS);
        if (products == null) {
            products = new LinkedList<>();
            request.getSession().setAttribute(RECENTLY_VIEWED_PRODUCTS, products);
        }
        return products;
    }
}
