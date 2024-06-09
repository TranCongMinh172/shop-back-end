package com.example.shop.repositories;

import com.example.shop.models.OrderVoucher;
import com.example.shop.models.idClass.OrderVoucherId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderVoucherRepository extends JpaRepository<OrderVoucher, OrderVoucherId> {
}