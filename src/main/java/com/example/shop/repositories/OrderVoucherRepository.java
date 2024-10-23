package com.example.shop.repositories;

import com.example.shop.models.OrderVoucher;
import com.example.shop.models.idClass.OrderVoucherId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderVoucherRepository extends BaseRepository<OrderVoucher, OrderVoucherId> {
    List<OrderVoucher> findAllByOrderId(String id);
}