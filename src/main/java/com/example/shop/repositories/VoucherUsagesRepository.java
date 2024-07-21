package com.example.shop.repositories;

import com.example.shop.models.VoucherUsages;
import com.example.shop.models.idClass.UserVoucherId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoucherUsagesRepository extends JpaRepository<VoucherUsages, UserVoucherId> {
}