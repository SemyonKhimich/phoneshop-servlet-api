package com.es.phoneshop.model.recently.viewed;

import javax.servlet.http.HttpServletRequest;

public interface RecentlyViewedProductsService {
    void add(RecentlyViewedProducts products, Long id);

    RecentlyViewedProducts getRecentlyViewedProducts(HttpServletRequest request);
}
