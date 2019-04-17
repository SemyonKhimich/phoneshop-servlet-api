package com.es.phoneshop.model.cart;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
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
        calculateTotalProductsPrice(cart);
        return cart;
    }

    @Override
    public void add(Cart cart, Long productId, Integer quantity) throws OutOfStockException, IncorrectValueException {
        if (quantity <= 0) {
            throw new IncorrectValueException(INCORRECT_VALUE_EXCEPTION_MESSAGE);
        }
        Optional<CartItem> optionalCartItem = getOptionalCartItem(cart, productId);
        if (optionalCartItem.isPresent()) {
            CartItem cartItem = optionalCartItem.get();
            if (cartItem.getQuantity() + quantity > cartItem.getProduct().getStock()) {
                throw new OutOfStockException(OUT_OF_STOCK_EXCEPTION_MESSAGE + productId);
            }
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        } else {
            Product product = productDao.getProduct(productId);
            if (quantity > product.getStock()) {
                throw new OutOfStockException(OUT_OF_STOCK_EXCEPTION_MESSAGE + productId);
            }
            CartItem cartItem = new CartItem(product, quantity);
            cart.getCartItems().add(cartItem);
        }
        calculateTotalProductsPrice(cart);
    }

    @Override
    public void update(Cart cart, Long productId, Integer quantity) throws OutOfStockException, IncorrectValueException {
        if (quantity <= 0) {
            throw new IncorrectValueException(INCORRECT_VALUE_EXCEPTION_MESSAGE);
        }
        Optional<CartItem> optionalCartItem = getOptionalCartItem(cart, productId);
        if (optionalCartItem.isPresent()) {
            CartItem cartItem = optionalCartItem.get();
            if (quantity > cartItem.getProduct().getStock()) {
                throw new OutOfStockException(OUT_OF_STOCK_EXCEPTION_MESSAGE + productId);
            }
            cartItem.setQuantity(quantity);
            calculateTotalProductsPrice(cart);
        }
    }

    @Override
    public void delete(Cart cart, Long productId) {
        cart.getCartItems().removeIf(cartItem -> cartItem.getProduct().getId().equals(productId));
        calculateTotalProductsPrice(cart);
    }

    void setProductDao(ProductDao productDao) {
        this.productDao = productDao;
    }

    private Optional<CartItem> getOptionalCartItem(Cart cart, Long productId) {
        return cart.getCartItems()
                .stream()
                .filter(cartItem -> productId.equals(cartItem.getProduct().getId()))
                .findAny();
    }

    @Override
    public void calculateTotalProductsPrice(Cart cart) {
        BigDecimal totalPrice = cart.getCartItems().stream()
                .map(cartItem -> cartItem.getProduct().getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        cart.setTotalProductsPrice(totalPrice);
    }

    @Override
    public void clearCart(HttpServletRequest request) {
        Cart cart = new Cart();
        request.getSession().setAttribute(CART, cart);
    }
}
