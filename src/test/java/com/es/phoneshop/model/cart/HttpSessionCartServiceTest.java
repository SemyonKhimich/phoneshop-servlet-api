package com.es.phoneshop.model.cart;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
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

    private List<CartItem> cartItems;
    private HttpSessionCartService cartService = HttpSessionCartService.getInstance();
    private static final Long ID = 1L;
    private static final Integer STOCK = 10;
    private static final String CART = "cart";

    @Before
    public void setup() {
        cartItems = new ArrayList<>();
        when(request.getSession()).thenReturn(session);
        cartService.setProductDao(productDao);
        when(productDao.getProduct(ID)).thenReturn(product);
        when(product.getStock()).thenReturn(STOCK);
    }

    @Test
    public void testGetCartNewCart() {
        cartService.getCart(request);
        verify(session).setAttribute(eq(CART), any(Cart.class));
    }

    @Test(expected = OutOfStockException.class)
    public void testAddOutOfStockException() throws OutOfStockException, IncorrectValueException {
        cartService.add(cart, ID, STOCK + 1);
    }

    @Test(expected = IncorrectValueException.class)
    public void testAddIncorrectValueException() throws OutOfStockException, IncorrectValueException {
        cartService.add(cart, ID, 0);
    }

    @Test(expected = OutOfStockException.class)
    public void testAddOutOfStockException2() throws OutOfStockException, IncorrectValueException {
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
    public void testAddNewCartItem() throws OutOfStockException, IncorrectValueException {
        List<CartItem> mockCartItems = mock(List.class);
        when(cart.getCartItems()).thenReturn(mockCartItems);
        cartService.add(cart, ID, STOCK);
        verify(mockCartItems).add(any(CartItem.class));
    }

    @Test
    public void testAddModifyCartItem() throws OutOfStockException, IncorrectValueException {
        Product productFromCartItem = mock(Product.class);
        CartItem cartItem = mock(CartItem.class);
        when(cartItem.getProduct()).thenReturn(productFromCartItem);
        when(cartItem.getQuantity()).thenReturn(STOCK / 2);
        when(productFromCartItem.getPrice()).thenReturn(BigDecimal.ONE);
        when(productFromCartItem.getId()).thenReturn(ID);
        when(productFromCartItem.getStock()).thenReturn(STOCK);
        cartItems.add(cartItem);
        when(cart.getCartItems()).thenReturn(cartItems);
        cartService.add(cart, ID, STOCK / 2);
        verify(cartItem).setQuantity(STOCK);
    }

    @Test
    public void testDelete() {
        Product productFromCartItem = mock(Product.class);
        CartItem cartItem = mock(CartItem.class);
        when(cartItem.getProduct()).thenReturn(productFromCartItem);
        when(productFromCartItem.getId()).thenReturn(ID);
        cartItems.add(cartItem);
        when(cart.getCartItems()).thenReturn(cartItems);
        cartService.delete(cart, ID);
        assertTrue(cartItems.isEmpty());
    }

    @Test(expected = IncorrectValueException.class)
    public void testUpdateIncorrectValueException() throws OutOfStockException, IncorrectValueException {
        cartService.update(cart, ID, -1);
    }

    @Test(expected = OutOfStockException.class)
    public void testUpdateOutOfStockException() throws OutOfStockException, IncorrectValueException {
        Product productFromCartItem = mock(Product.class);
        CartItem cartItem = mock(CartItem.class);
        when(cartItem.getProduct()).thenReturn(productFromCartItem);
        when(productFromCartItem.getId()).thenReturn(ID);
        cartItems.add(cartItem);
        when(cart.getCartItems()).thenReturn(cartItems);
        cartService.update(cart, ID, STOCK + 1);
    }

    @Test
    public void testUpdatedSuccessfully() throws OutOfStockException, IncorrectValueException {
        Product productFromCartItem = mock(Product.class);
        CartItem cartItem = mock(CartItem.class);
        when(cartItem.getProduct()).thenReturn(productFromCartItem);
        when(productFromCartItem.getPrice()).thenReturn(BigDecimal.ONE);
        when(productFromCartItem.getId()).thenReturn(ID);
        when(productFromCartItem.getStock()).thenReturn(STOCK);
        cartItems.add(cartItem);
        when(cart.getCartItems()).thenReturn(cartItems);
        cartService.update(cart, ID, STOCK - 1);
        verify(cartItem).setQuantity(STOCK - 1);
    }
    @Test
    public void testClearCart(){
        cartService.clearCart(request);
        verify(session).setAttribute(eq(CART), any(Cart.class));
    }
}
