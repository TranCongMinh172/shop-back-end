package com.example.shop.service.interfaces;

import com.example.shop.dtos.requests.OrderDto;
import com.example.shop.exceptions.DataNotFoundException;
import com.example.shop.exceptions.OutOfInStockException;
import com.example.shop.models.Order;
import com.example.shop.models.OrderDetail;
import com.example.shop.models.enums.OrderStatus;

import java.util.List;

public interface OrderService extends BaseService<Order, String>{
    Order save(OrderDto orderDto) throws DataNotFoundException, OutOfInStockException;
    Order updateStatusOrder(String id, OrderStatus status) throws DataNotFoundException;
    List<OrderDetail> getOrderDetailsByOrderId(String orderId);
}
