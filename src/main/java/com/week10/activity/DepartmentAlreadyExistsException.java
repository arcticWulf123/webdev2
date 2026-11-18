package com.week10.activity;

public class DepartmentAlreadyExistsException extends RuntimeException {

    public DepartmentAlreadyExistsException(String message) {
        super(message);
    }
}
