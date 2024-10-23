package com.example.shop.repositories;

import com.example.shop.models.OrderDetail;
import com.example.shop.models.idClass.OrderDetailId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailRepository extends BaseRepository<OrderDetail, OrderDetailId> {
    List<OrderDetail> findByOrderId(String id);
}