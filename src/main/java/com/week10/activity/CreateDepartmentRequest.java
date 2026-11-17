package com.week10.activity;

public class CreateDepartmentRequest {
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