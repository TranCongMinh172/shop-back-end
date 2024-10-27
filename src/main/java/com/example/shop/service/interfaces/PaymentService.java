package com.example.shop.service.interfaces;

import com.example.shop.exceptions.DataNotFoundException;
import jakarta.servlet.http.HttpServletRequest;

import java.io.UnsupportedEncodingException;

public interface PaymentService {
    String payment(String orderId, HttpServletRequest req) throws UnsupportedEncodingException;
    String paymentSuccess(HttpServletRequest req) throws DataNotFoundException;
}
