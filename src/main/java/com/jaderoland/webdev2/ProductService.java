package com.jaderoland.webdev2;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
public class ProductService {
    private final ShopProperties shopProperties;
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository, ShopProperties shopProperties) {
        this.productRepository = productRepository;
        this.shopProperties = shopProperties;
    }

    public void getShopInfo() {
        System.out.println(shopProperties.getName());
        System.out.println(shopProperties.getCurrency());
    }

    public List<Product> filterProducts(double threshold) {
        ProductRepository repository = new ProductRepository();
        Product laptop = new Product("Laptop", 6000);
        Product phone = new Product("Phone", 7000);
        Product smartWatch = new Product("SmartWatch", 2000);
        repository.addProduct(smartWatch);
        repository.addProduct(laptop);
        repository.addProduct(phone);
        List<Product> products = repository.getProducts();
        return products.stream().filter(p -> p.getPrice() > threshold).collect(Collectors.toList());
    }
}
