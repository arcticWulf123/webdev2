package com.week8.activity;

public class PetNotFoundException extends RuntimeException {
    
    public PetNotFoundException (String message) {
        super(message);
    }
}
