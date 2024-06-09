package com.example.shop.service.impls;

import com.example.shop.models.Order;
import com.example.shop.service.interfaces.OrderService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl extends  BaseServiceImpl<Order,String>implements OrderService {
    public OrderServiceImpl(JpaRepository<Order, String> repository) {
        super(repository);
    }
}
