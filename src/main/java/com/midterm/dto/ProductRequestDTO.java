package com.midterm.dto;

import java.math.BigDecimal;

import com.midterm.model.ProductStatus;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record ProductRequestDTO(

        @NotBlank(message = "name is required")
        @Size(min = 2, max = 100, message = "name must be between 2 and 100 characters")
        String name,

        @NotBlank(message = "sku is required")
        @Pattern(regexp = "^[A-Z0-9]+(-[A-Z0-9]+)*$",
                 message = "sku must be uppercase letters/digits, e.g. PROD-101")
        String sku,

        @NotNull(message = "price is required")
        @DecimalMin(value = "1.0", message = "price must be at least 1")
        @Digits(integer = 10, fraction = 2, message = "price allows at most 2 decimal places")
        BigDecimal price,

        @NotNull(message = "stockQuantity is required")
        @Min(value = 0, message = "stockQuantity cannot be negative")
        Integer stockQuantity,

        ProductStatus status,

        @NotNull(message = "categoryId is required")
        @Positive(message = "categoryId must be a positive number")
        Long categoryId) {
}
