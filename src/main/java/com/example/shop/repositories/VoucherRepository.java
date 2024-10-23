package com.example.shop.repositories;

import com.example.shop.models.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoucherRepository extends BaseRepository<Voucher, Long> {
}