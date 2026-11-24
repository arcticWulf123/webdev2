package com.midterm.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.midterm.dto.ProductRequestDTO;
import com.midterm.dto.ProductResponseDTO;
import com.midterm.exception.ResourceNotFoundException;
import com.midterm.model.Category;
import com.midterm.model.Product;
import com.midterm.model.ProductStatus;
import com.midterm.repository.CategoryRepository;
import com.midterm.repository.ProductRepository;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository,
                          CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Transactional(readOnly = true)
    public Page<ProductResponseDTO> findAll(Long categoryId, Pageable pageable) {
        Page<Product> page = (categoryId == null)
                ? productRepository.findAll(pageable)
                : productRepository.findByCategoryId(categoryId, pageable);

        return page.map(ProductService::toResponse);
    }

    @Transactional(readOnly = true)
    public ProductResponseDTO findById(Long id) {
        return toResponse(getProduct(id));
    }

    @Transactional(readOnly = true)
    public List<ProductResponseDTO> findByStatus(ProductStatus status) {
        return productRepository.findByStatus(status).stream()
                .map(ProductService::toResponse)
                .toList();
    }

    @Transactional
    public ProductResponseDTO create(ProductRequestDTO request) {
        Category category = getCategory(request.categoryId());

        Product product = new Product(
                request.name(),
                request.sku(),
                request.price(),
                request.stockQuantity(),
                category);

        if (request.status() != null) {
            product.setStatus(request.status());
        }

        return toResponse(productRepository.save(product));
    }

    @Transactional
    public ProductResponseDTO update(Long id, ProductRequestDTO request) {
        Product product = getProduct(id);
        Category category = getCategory(request.categoryId());

        product.setName(request.name());
        product.setSku(request.sku());
        product.setPrice(request.price());
        product.setStockQuantity(request.stockQuantity());
        product.setCategory(category);

        if (request.status() != null) {
            product.setStatus(request.status());
        }

        return toResponse(product);
    }

    @Transactional
    public void delete(Long id) {
        productRepository.delete(getProduct(id));
    }

    private Product getProduct(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
    }

    private Category getCategory(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
    }

    private static ProductResponseDTO toResponse(Product product) {
        return new ProductResponseDTO(
                product.getId(),
                product.getName(),
                product.getSku(),
                product.getPrice(),
                product.getStockQuantity(),
                product.getStatus(),
                product.getCategory().getId(),
                product.getCategory().getName());
    }
}
