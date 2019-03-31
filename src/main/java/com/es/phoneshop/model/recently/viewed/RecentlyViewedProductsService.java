package com.es.phoneshop.model.recently.viewed;

import com.es.phoneshop.model.product.Product;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface RecentlyViewedProductsService {
    void add(List<Product> recentlyViewedProducts, Long id);

    List<Product> getRecentlyViewedProducts(HttpServletRequest request);
}
