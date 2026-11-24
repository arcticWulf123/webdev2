package com.midterm.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.midterm.dto.CategoryRequestDTO;
import com.midterm.dto.CategoryResponseDTO;
import com.midterm.exception.CategoryHasProductsException;
import com.midterm.exception.ResourceNotFoundException;
import com.midterm.model.Category;
import com.midterm.repository.CategoryRepository;
import com.midterm.repository.ProductRepository;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public CategoryService(CategoryRepository categoryRepository,
                           ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    @Transactional(readOnly = true)
    public List<CategoryResponseDTO> findAll() {
        return categoryRepository.findAll().stream()
                .map(CategoryService::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public CategoryResponseDTO findById(Long id) {
        return toResponse(getCategory(id));
    }

    @Transactional
    public CategoryResponseDTO create(CategoryRequestDTO request) {
        Category category = new Category(request.name(), request.description());
        Category saved = categoryRepository.save(category);
        return toResponse(saved);
    }

    @Transactional
    public CategoryResponseDTO update(Long id, CategoryRequestDTO request) {
        Category category = getCategory(id);
        category.setName(request.name());
        category.setDescription(request.description());
        return toResponse(category);
    }

    @Transactional
    public void delete(Long id) {
        Category category = getCategory(id);

        if (productRepository.existsByCategoryId(id)) {
            throw new CategoryHasProductsException(
                    "Cannot delete category " + id + " because it still has products");
        }

        categoryRepository.delete(category);
    }

    private Category getCategory(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
    }

    private static CategoryResponseDTO toResponse(Category category) {
        return new CategoryResponseDTO(
                category.getId(),
                category.getName(),
                category.getDescription());
    }
}
