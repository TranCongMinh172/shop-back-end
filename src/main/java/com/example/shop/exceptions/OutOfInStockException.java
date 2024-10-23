package com.example.shop.exceptions;

public class OutOfInStockException extends Exception {
    public OutOfInStockException(String message) {
        super(message);
    }
}
