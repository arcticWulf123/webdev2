package com.jaderoland.webdev2;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService (ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    public List<Product> filterProducts(double threshold) {
        ProductRepository repository = new ProductRepository();
        List<Product> products = repository.getProducts();
        return products.stream().filter(p -> p.getPrice() > threshold).collect(Collectors.toList());
    }
}
