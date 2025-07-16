package com.example.mailguard.exception;

public class UserPreferenceExistException extends RuntimeException {
    public UserPreferenceExistException(String message) {
        super(message);
    }
}
