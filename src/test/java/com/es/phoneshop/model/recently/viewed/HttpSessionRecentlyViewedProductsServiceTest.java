package com.es.phoneshop.model.recently.viewed;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class HttpSessionRecentlyViewedProductsServiceTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpSession session;
    @Mock
    private ArrayListProductDao productDao;
    @Mock
    private Product product;
    @Mock
    private RecentlyViewedProducts recentlyViewedProducts;
    @Mock
    private List<Product> productList;

    private HttpSessionRecentlyViewedProductsService recentlyViewedProductsService = new HttpSessionRecentlyViewedProductsService();
    private static final Long ID = 1L;

    @Before
    public void setup() {
        when(request.getSession()).thenReturn(session);
        ArrayListProductDao.setInstance(productDao);
        when(productDao.getProduct(ID)).thenReturn(product);
        when(recentlyViewedProducts.getProducts()).thenReturn(productList);
        when(productList.contains(product)).thenReturn(false);
    }

    @Test
    public void testGet() {
        recentlyViewedProductsService.getRecentlyViewedProducts(request);
        verify(session).setAttribute(eq(HttpSessionRecentlyViewedProductsService.RECENTLY_VIEWED_PRODUCTS), any(RecentlyViewedProducts.class));
    }

    @Test
    public void testAdd() {
        when(productList.size()).thenReturn(HttpSessionRecentlyViewedProductsService.NUMBER_OF_RECENTLY_VIEWED_PRODUCTS);
        recentlyViewedProductsService.add(recentlyViewedProducts, ID);
        verify(productList).remove(0);
        verify(productList).add(product);
    }
}
