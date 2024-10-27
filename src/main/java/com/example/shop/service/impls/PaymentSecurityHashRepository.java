package com.example.shop.service.impls;

import com.example.shop.models.PaymentSecurityHash;
import com.example.shop.repositories.BaseRepository;

import java.util.Optional;

public interface PaymentSecurityHashRepository extends BaseRepository<PaymentSecurityHash, Long> {
    Optional<PaymentSecurityHash> findByOrderIdAndHashCode(String orderId, String hash);
    Optional<PaymentSecurityHash> findByOrderId(String orderId);
}
