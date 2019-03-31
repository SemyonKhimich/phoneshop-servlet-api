package com.es.phoneshop.model.cart;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class HttpSessionCartService implements CartService {
    static final String CART = "cart";
    static final String EXCEPTION_MESSAGE = "Not enough stock for product with id ";

    private static HttpSessionCartService instance = new HttpSessionCartService();

    public static HttpSessionCartService getInstance() {
        return instance;
    }

    public static void setInstance(HttpSessionCartService instance) {
        HttpSessionCartService.instance = instance;
    }


    @Override
    public Cart getCart(HttpServletRequest request) {
        Cart cart = (Cart) request.getSession().getAttribute(CART);
        if (cart == null) {
            cart = new Cart();
            request.getSession().setAttribute(CART, cart);
        }
        return cart;
    }

    @Override
    public void add(Cart cart, Long productId, Integer quantity) throws OutOfStockException {
        ArrayListProductDao productDao = ArrayListProductDao.getInstance();
        Product product = productDao.getProduct(productId);
        Optional<CartItem> optionalCartItem = cart.getCartItems()
                .stream()
                .filter(cartItem -> productId.equals(cartItem.getProduct().getId()))
                .findAny();
        if (optionalCartItem.isPresent()) {
            CartItem cartItem = optionalCartItem.get();
            if (cartItem.getQuantity() + quantity > product.getStock()) {
                throw new OutOfStockException(EXCEPTION_MESSAGE + product.getId());
            }
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        } else {
            if (quantity > product.getStock()) {
                throw new OutOfStockException(EXCEPTION_MESSAGE + product.getId());
            }
            CartItem cartItem = new CartItem(product, quantity);
            cart.getCartItems().add(cartItem);
        }
    }
}
