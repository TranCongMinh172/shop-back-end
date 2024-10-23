package com.example.shop.controllers;


import com.example.shop.dtos.requests.OrderDto;
import com.example.shop.dtos.requests.OrderUpdateDto;
import com.example.shop.exceptions.DataNotFoundException;
import com.example.shop.exceptions.OutOfInStockException;

import com.example.shop.service.interfaces.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.example.shop.dtos.requests.responses.ResponseSuccess;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping()
    public ResponseSuccess<?> createOrder(@RequestBody @Valid OrderDto orderDto) throws DataNotFoundException, OutOfInStockException {
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "Create a order successfully",
                orderService.save(orderDto)
        );
    }
    @GetMapping("/{id}")
    public ResponseSuccess<?> getOrderById(@PathVariable String id) throws DataNotFoundException {
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "success",
                orderService.findById(id)
        );
    }
    @GetMapping
    public ResponseSuccess<?> getAllOrders(@RequestParam(defaultValue = "1") int pageNo,
                                           @RequestParam(defaultValue = "10") int pageSize,
                                           @RequestParam(required = false) String[] sort,
                                           @RequestParam(required = false) String[] search) throws DataNotFoundException {
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "success",
                orderService.getPageData(pageNo, pageSize, search, sort)
        );
    }
    @PutMapping("/{id}")
    public ResponseSuccess<?> updateStatusOrder(@PathVariable String id,
                                                @RequestBody OrderUpdateDto orderDto) throws DataNotFoundException {
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "success",
                orderService.updateStatusOrder(id, orderDto.getStatus())
        );
    }
    @PatchMapping("/{id}")
    public ResponseSuccess<?> updateStatusOrder(@PathVariable String id, @RequestBody @Valid Map<String, ?> data) throws DataNotFoundException {
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "success",
                orderService.updatePatch(id, data)
        );
    }
}
