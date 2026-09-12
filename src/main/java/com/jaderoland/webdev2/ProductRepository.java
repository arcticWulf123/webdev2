package com.jaderoland.webdev2;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class ProductRepository {
    private final List<Product> products = new ArrayList<>();

    public ProductRepository() {
        products.add(new Product("Smartphone", 2000, 1L, new Category("Electronics")));
        products.add(new Product("Laptop", 5000, 2L, new Category("Electronics")));
        products.add(new Product("Smartwatch", 2500, 3L, new Category("Electronics")));
        products.add(new Product("Airpods", 500, 4L, new Category("Electronics")));
    }

    public List<Product> getProducts() {
        return products;
    }

    public void addProducts(Product product) {
        products.add(product);
    }
}
