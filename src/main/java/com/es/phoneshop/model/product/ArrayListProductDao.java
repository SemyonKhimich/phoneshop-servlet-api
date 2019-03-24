package com.es.phoneshop.model.product;

import java.util.*;
import java.util.logging.LogManager;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ArrayListProductDao implements ProductDao {
    private List<Product> products;
    private static ProductDao instance = new ArrayListProductDao();

    public static ProductDao getInstance() {
        return instance;
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

    @Override
    public List<Product> findProducts(String query, String order, String field) {
        Stream<Product> productStream = products.stream()
                .filter(product -> product.getPrice() != null && product.getStock() > 0);
        if (query != null) {
            String words[] = query.toLowerCase().split(" ");
            if (order == null && field == null) {
                Map<Product, Long> productLongMap = new HashMap<>();
                productStream
                        .forEach(product -> productLongMap.put(product, words.length - Arrays.stream(words)
                                .filter(word -> product.getDescription().toLowerCase().contains(word)).count()));
                return productLongMap.entrySet()
                        .stream().filter(productLongEntry -> productLongEntry.getValue() < words.length)
                        .sorted(Map.Entry.comparingByValue())
                        .map(Map.Entry::getKey).collect(Collectors.toList());
            } else {
                return productStream
                        .filter(product -> Arrays.stream(words)
                                .filter(word -> product.getDescription().toLowerCase().contains(word)).count() > 0)
                        .sorted(getComparator(order, field)).collect(Collectors.toList());
            }
        } else {
            return productStream.sorted(getComparator(order, field)).collect(Collectors.toList());
        }

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
