package com.jaderoland.webdev2;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class ProductRepository {
    private List<Product> products = new ArrayList<>();

    public ProductRepository(List<Product> products) {
        this.products = products;
    }

    public List<Product> getProducts() {
        return products;
    }
}
