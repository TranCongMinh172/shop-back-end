package com.example.shop.repositories;

import com.example.shop.models.OrderDetail;
import com.example.shop.models.idClass.OrderDetailId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, OrderDetailId> {
}