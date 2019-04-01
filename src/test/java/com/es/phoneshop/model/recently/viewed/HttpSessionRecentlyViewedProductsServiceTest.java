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
    private List<Product> productList;

    private HttpSessionRecentlyViewedProductsService recentlyViewedProductsService = new HttpSessionRecentlyViewedProductsService();
    private static final Long ID = 1L;

    @Before
    public void setup() {
        when(request.getSession()).thenReturn(session);
        ArrayListProductDao.setInstance(productDao);
        when(productDao.getProduct(ID)).thenReturn(product);
    }

    @Test
    public void testGet() {
        recentlyViewedProductsService.getRecentlyViewedProducts(request);
        verify(session).setAttribute(eq(HttpSessionRecentlyViewedProductsService.RECENTLY_VIEWED_PRODUCTS), any(List.class));
    }

    @Test
    public void testAdd() {
        when(productList.contains(product)).thenReturn(false);
        when(productList.size()).thenReturn(HttpSessionRecentlyViewedProductsService.NUMBER_OF_RECENTLY_VIEWED_PRODUCTS);
        recentlyViewedProductsService.add(productList, ID);
        verify(productList).remove(0);
        verify(productList).add(product);
    }
    @Test
    public  void testAddListContainsProduct(){
        when(productList.contains(product)).thenReturn(true);
        recentlyViewedProductsService.add(productList, ID);
        verify(productList).remove(product);
        verify(productList).add(product);
    }
}
