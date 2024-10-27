package com.example.shop.controllers;

import com.example.shop.service.interfaces.PaymentService;
import com.example.shop.dtos.requests.responses.ResponseSuccess;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping("/vnp/{orderId}")
    public ResponseSuccess<?> payment(@PathVariable String orderId, HttpServletRequest req)
    throws Exception {
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "success",
                paymentService.payment(orderId, req)
        );
    }

    @GetMapping("/vnp/payment-success")
    public ResponseSuccess<?> paymentSuccess(HttpServletRequest req) throws Exception {
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "success",
                paymentService.paymentSuccess(req)
        );
    }
}
