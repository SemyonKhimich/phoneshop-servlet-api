package com.es.phoneshop.model.product;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ArrayListProductDao implements ProductDao {
    private List<Product> products;
    private static ArrayListProductDao instance = new ArrayListProductDao();

    public static ArrayListProductDao getInstance() {
        return instance;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    private ArrayListProductDao() {
        products = new ArrayList<>();
    }

    @Override
    public Product getProduct(Long id) {
        return products.stream()
                .filter(product -> product.getId().equals(id))
                .findFirst().orElseThrow(() -> new ProductNotFoundException("Product with id " + id + " not found"));
    }

    private Comparator<Product> getComparator(String order, String field) {
        Comparator<Product> comparator;
        if ("description".equals(field)) {
            comparator = Comparator.comparing(Product::getDescription);
        } else if ("price".equals(field)) {
            comparator = Comparator.comparing(Product::getPrice);
        } else {
            comparator = Comparator.comparing(Product::getId);
        }
        if ("desc".equals(order)) {
            return comparator.reversed();
        }
        return comparator;
    }

    private Stream<Map.Entry<Product, Long>> getStreamOfMatchedProduct(String query) {
        Map<Product, Long> productLongMap = new HashMap<>();
        String words[] = query.split(" ");
        getValidProductStream()
                .forEach(product -> productLongMap.put(product, words.length - Arrays.stream(words)
                        .filter(word -> product.getDescription().toLowerCase().contains(word)).count()));
        return productLongMap.entrySet()
                .stream().filter(productLongEntry -> productLongEntry.getValue() < words.length);
    }

    private Stream<Product> getValidProductStream() {
        return products.stream()
                .filter(product -> product.getPrice() != null && product.getStock() > 0);
    }

    private List<Product> getSortedProducts(String order, String field) {
        return getValidProductStream().sorted(getComparator(order, field)).collect(Collectors.toList());
    }

    private List<Product> getSearchResult(String query) {
        return getStreamOfMatchedProduct(query)
                .sorted(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey).collect(Collectors.toList());
    }

    private List<Product> getSortedSearchResult(String query, String order, String field) {
        String words[] = query.split(" ");
        return getValidProductStream()
                .filter(product -> Arrays.stream(words)
                        .anyMatch(word -> product.getDescription().toLowerCase().contains(word)))
                .sorted(getComparator(order, field)).collect(Collectors.toList());
    }

    @Override
    public List<Product> findProducts(String query, String order, String field) {
        if (query == null) {
            return getSortedProducts(order, field);
        }
        if (order == null && field == null) {
            return getSearchResult(query);
        }
        return getSortedSearchResult(query, order, field);

    }

    @Override
    public void save(Product product) {
        if (product != null && products.stream().noneMatch(element -> element.getId().equals(product.getId()))) {
            products.add(product);
        }
    }

    @Override
    public void delete(Long id) {
        products.removeIf(product -> product.getId().equals(id));
    }
}
