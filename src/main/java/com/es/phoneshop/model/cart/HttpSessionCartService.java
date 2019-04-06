package com.es.phoneshop.model.cart;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class HttpSessionCartService implements CartService {
    private static final String CART = "cart";
    private static final String OUT_OF_STOCK_EXCEPTION_MESSAGE = "Not enough stock for product with id ";
    private static final String INCORRECT_VALUE_EXCEPTION_MESSAGE = "Value must be positive";
    private ProductDao productDao;

    private HttpSessionCartService() {
        productDao = ArrayListProductDao.getInstance();
    }

    private static HttpSessionCartService instance = new HttpSessionCartService();

    public static HttpSessionCartService getInstance() {
        return instance;
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
    public void add(Cart cart, Long productId, Integer quantity) throws OutOfStockException, IncorrectValueException {
        if (quantity <= 0) {
            throw new IncorrectValueException(INCORRECT_VALUE_EXCEPTION_MESSAGE);
        }
        Product product = productDao.getProduct(productId);
        Optional<CartItem> optionalCartItem = cart.getCartItems()
                .stream()
                .filter(cartItem -> productId.equals(cartItem.getProduct().getId()))
                .findAny();
        if (optionalCartItem.isPresent()) {
            CartItem cartItem = optionalCartItem.get();
            if (cartItem.getQuantity() + quantity > product.getStock()) {
                throw new OutOfStockException(OUT_OF_STOCK_EXCEPTION_MESSAGE + product.getId());
            }
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        } else {
            if (quantity > product.getStock()) {
                throw new OutOfStockException(OUT_OF_STOCK_EXCEPTION_MESSAGE + product.getId());
            }
            CartItem cartItem = new CartItem(product, quantity);
            cart.getCartItems().add(cartItem);
        }
    }

    @Override
    public void update(Cart cart, Long productId, Integer quantity) throws OutOfStockException, IncorrectValueException {
        if (quantity <= 0) {
            throw new IncorrectValueException(INCORRECT_VALUE_EXCEPTION_MESSAGE);
        }
        Product product = productDao.getProduct(productId);
        Optional<CartItem> optionalCartItem = cart.getCartItems()
                .stream()
                .filter(cartItem -> productId.equals(cartItem.getProduct().getId()))
                .findAny();
        if (optionalCartItem.isPresent()) {
            CartItem cartItem = optionalCartItem.get();
            if (quantity > product.getStock()) {
                throw new OutOfStockException(OUT_OF_STOCK_EXCEPTION_MESSAGE + product.getId());
            }
            cartItem.setQuantity(quantity);
        }
    }

    @Override
    public void delete(Cart cart, Long productId) {
        cart.getCartItems().removeIf(cartItem -> cartItem.getProduct().getId().equals(productId));
    }

    void setProductDao(ProductDao productDao) {
        this.productDao = productDao;
    }
}
