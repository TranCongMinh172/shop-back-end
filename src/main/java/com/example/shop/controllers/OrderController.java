package com.example.shop.controllers;


import com.example.shop.dto.requests.OrderDto;
import com.example.shop.exceptions.DataNotFoundException;
import com.example.shop.mappers.OrderMapper;
import com.example.shop.models.Order;

import com.example.shop.service.interfaces.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.example.shop.dto.requests.responses.ResponseSuccess;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final OrderMapper  orderMapper;

    @PostMapping()
    public ResponseSuccess<?> createOrder(@RequestBody @Valid OrderDto orderDto) throws DataNotFoundException {
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "Create a order successfully",
                orderService.save(orderDto)
        );
    }

}
