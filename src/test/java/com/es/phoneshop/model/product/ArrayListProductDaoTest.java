package com.es.phoneshop.model.product;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.*;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class ArrayListProductDaoTest {
    private ProductDao productDao;

    @Before
    public void setup() {
        productDao = ArrayListProductDao.getInstance();
    }


    @Test
    public void testFindProductsNoResults() {
        assertTrue(productDao.findProducts(null, null, null).isEmpty());
    }

    @Test
    public void testSaveNullProduct() {
        productDao.save(null);
        assertTrue(productDao.findProducts(null, null, null).isEmpty());
    }

    @Test
    public void testProductSave() {
        Product product = mock(Product.class);
        when(product.getId()).thenReturn(1L);
        when(product.getPrice()).thenReturn(BigDecimal.ONE);
        when(product.getStock()).thenReturn(1);
        productDao.save(product);
        assertEquals((long) productDao.findProducts(null, null, null).size(), 1L);
    }

    @Test
    public void testDelete() {
        Product product = mock(Product.class);
        when(product.getId()).thenReturn(1L);
        when(product.getPrice()).thenReturn(BigDecimal.ONE);
        when(product.getStock()).thenReturn(1);
        productDao.save(product);
        productDao.delete(1L);
        assertTrue(productDao.findProducts(null, null, null).isEmpty());
    }

    @Test
    public void testSaveSameId() {
        Product product = mock(Product.class);
        when(product.getId()).thenReturn(1L);
        when(product.getPrice()).thenReturn(BigDecimal.ONE);
        when(product.getStock()).thenReturn(1);
        productDao.save(product);
        productDao.save(product);
        assertEquals((long) productDao.findProducts(null, null, null).size(), 1L);
    }

    @Test
    public void testNullPrice() {
        Product product = mock(Product.class);
        when(product.getId()).thenReturn(1L);
        when(product.getStock()).thenReturn(1);
        productDao.save(product);
        assertTrue(productDao.findProducts(null, null, null).isEmpty());
    }

    @Test(expected = ProductNotFoundException.class)
    public void testException() throws ProductNotFoundException {
        productDao.getProduct(1L);

    }

    @Test
    public void testSortByPrice() {
        Product product1 = mock(Product.class);
        Product product2 = mock(Product.class);
        when(product1.getId()).thenReturn(1L);
        when(product1.getPrice()).thenReturn(new BigDecimal(2));
        when(product1.getStock()).thenReturn(1);

        when(product2.getId()).thenReturn(2L);
        when(product2.getStock()).thenReturn(1);
        when(product2.getPrice()).thenReturn(new BigDecimal(1));
        productDao.save(product1);
        productDao.save(product2);
        assertEquals(productDao.findProducts(null, "asc", "price").get(0).getId().longValue(), 2L);
    }

    @Test
    public void testSortByDescription() {
        Product product1 = mock(Product.class);
        Product product2 = mock(Product.class);
        when(product1.getId()).thenReturn(1L);
        when(product1.getPrice()).thenReturn(new BigDecimal(2));
        when(product1.getStock()).thenReturn(1);
        when(product1.getDescription()).thenReturn("a");

        when(product2.getId()).thenReturn(2L);
        when(product2.getStock()).thenReturn(1);
        when(product2.getPrice()).thenReturn(new BigDecimal(1));
        when(product2.getDescription()).thenReturn("z");
        productDao.save(product1);
        productDao.save(product2);
        assertEquals(productDao.findProducts(null, "desc", "description").get(0).getId().longValue(), 2L);
    }
}
