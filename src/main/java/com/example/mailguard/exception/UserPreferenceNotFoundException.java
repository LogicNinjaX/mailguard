package com.example.mailguard.exception;

public class UserPreferenceNotFoundException extends RuntimeException {
    public UserPreferenceNotFoundException(String message) {
        super(message);
    }
}
