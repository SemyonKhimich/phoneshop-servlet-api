package com.es.phoneshop.web;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.ServletContextEvent;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ProductDemodataServletContextListenerTest {
    @Mock
    private ServletContextEvent servletContextEvent;
    @Mock
    private ArrayListProductDao arrayListProductDao;

    private ProductDemodataServletContextListener listener = new ProductDemodataServletContextListener();

    @Test
    public void test() {
        ArrayListProductDao.setInstance(arrayListProductDao);
        listener.contextInitialized(servletContextEvent);
        verify(arrayListProductDao, times(13)).save(any(Product.class));
    }
}
