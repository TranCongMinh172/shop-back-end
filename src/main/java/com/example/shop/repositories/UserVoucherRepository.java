package com.example.shop.repositories;

import com.example.shop.models.UserVoucher;
import com.example.shop.models.idClass.UserVoucherId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserVoucherRepository extends JpaRepository<UserVoucher, UserVoucherId> {
}