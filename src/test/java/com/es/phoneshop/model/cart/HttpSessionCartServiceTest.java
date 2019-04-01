package com.es.phoneshop.model.cart;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class HttpSessionCartServiceTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpSession session;
    @Mock
    private Cart cart;
    @Mock
    private ArrayListProductDao productDao;
    @Mock
    private Product product;

    private List<CartItem> cartItems = new ArrayList<>();
    private HttpSessionCartService cartService = new HttpSessionCartService();
    private static final Long ID = 1L;
    private static final Integer STOCK = 10;

    @Before
    public void setup() {
        when(request.getSession()).thenReturn(session);
        ArrayListProductDao.setInstance(productDao);
        when(productDao.getProduct(ID)).thenReturn(product);
        when(product.getId()).thenReturn(ID);
        when(product.getStock()).thenReturn(STOCK);
    }

    @Test
    public void testGetCartNewCart() {
        cartService.getCart(request);
        verify(session).setAttribute(eq(HttpSessionCartService.CART), any(Cart.class));
    }

    @Test(expected = OutOfStockException.class)
    public void testAddOutOfStockException() throws OutOfStockException {
        cartService.add(cart, ID, STOCK + 1);
    }

    @Test(expected = OutOfStockException.class)
    public void testAddOutOfStockException2() throws OutOfStockException {
        Product productFromCartItem = mock(Product.class);
        CartItem cartItem = mock(CartItem.class);
        when(cartItem.getProduct()).thenReturn(productFromCartItem);
        when(cartItem.getQuantity()).thenReturn(STOCK);
        when(productFromCartItem.getId()).thenReturn(ID);
        cartItems.add(cartItem);
        when(cart.getCartItems()).thenReturn(cartItems);
        cartService.add(cart, ID, 1);
    }

    @Test
    public void testAddNewCartItem() throws OutOfStockException {
        List<CartItem> mockCartItems = mock(List.class);
        when(cart.getCartItems()).thenReturn(mockCartItems);
        cartService.add(cart, ID, STOCK);
        verify(mockCartItems).add(any(CartItem.class));
    }

    @Test
    public void testAddModifyCartItem() throws OutOfStockException {
        Product productFromCartItem = mock(Product.class);
        CartItem cartItem = mock(CartItem.class);
        when(cartItem.getProduct()).thenReturn(productFromCartItem);
        when(cartItem.getQuantity()).thenReturn(STOCK / 2);
        when(productFromCartItem.getId()).thenReturn(ID);
        cartItems.add(cartItem);
        when(cart.getCartItems()).thenReturn(cartItems);
        cartService.add(cart, ID, STOCK / 2);
        verify(cartItem).setQuantity(STOCK);
    }
}
