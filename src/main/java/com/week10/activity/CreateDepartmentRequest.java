package com.week10.activity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CreateDepartmentRequest {
    @NotBlank(message = "Name cannot be blank")
    @Size(max = 50, message = "Your name cannot exceed more than 50 characters")
    private String name;

    public CreateDepartmentRequest() {
    }

    public CreateDepartmentRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}