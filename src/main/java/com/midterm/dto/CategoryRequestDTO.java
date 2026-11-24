package com.midterm.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoryRequestDTO(

        @NotBlank(message = "name is required")
        @Size(min = 2, max = 50, message = "name must be between 2 and 50 characters")
        String name,

        @Size(max = 255, message = "description must be at most 255 characters")
        String description) {
}
