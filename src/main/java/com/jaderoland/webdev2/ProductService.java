package com.jaderoland.webdev2;

import java.util.List;

import org.springframework.stereotype.Service;
@Service 

public class ProductService {
    private final ProductRepository productRepository;
    
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> returnAllProducts () {
        return productRepository.getProducts();
    }

    public Product getProductById (Long id) {
        return productRepository.getProducts().stream().filter(p -> p.getProductId().equals(id)).findFirst().orElse(null);
    }

    public void addProduct (Product product) {
        productRepository.addProducts(product);
    }
}
