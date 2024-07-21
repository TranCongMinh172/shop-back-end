package com.example.shop.service.interfaces;

import com.example.shop.dto.requests.OrderDto;
import com.example.shop.exceptions.DataNotFoundException;
import com.example.shop.models.Order;

public interface OrderService extends BaseService<Order, String>{
    Order save(OrderDto orderDto) throws DataNotFoundException;
}
