package com.example.shop.controllers;


import com.example.shop.service.interfaces.OrderService;
import com.example.shop.dtos.requests.responses.ResponseSuccess;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/order-details")
@RequiredArgsConstructor
public class OrderDetailController {

    private final OrderService orderService;

    @GetMapping("/{orderId}")
    public ResponseSuccess<?> getOrderDetailsByOrderId(@PathVariable final String orderId) {
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "success",
                orderService.getOrderDetailsByOrderId(orderId)
        );
    }
}
