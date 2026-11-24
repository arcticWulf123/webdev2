package com.midterm.dto;

import java.math.BigDecimal;

import com.midterm.model.ProductStatus;

public record ProductResponseDTO(
        Long id,
        String name,
        String sku,
        BigDecimal price,
        Integer stockQuantity,
        ProductStatus status,
        Long categoryId,
        String categoryName) {
}
