package com.week10.activity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UpdateDepartmentRequest {
    @NotBlank(message = "Name cannot be blank")
    @Size(max = 50, message = "Your name cannot exceed more than 50 characters")
    private String name;

    public UpdateDepartmentRequest() {
    }

    public UpdateDepartmentRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}