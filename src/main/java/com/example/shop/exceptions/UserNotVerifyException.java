package com.example.shop.exceptions;

public class UserNotVerifyException extends RuntimeException {
    public UserNotVerifyException(String message) {
        super(message);
    }
}
