package com.example.shop.service.impls;

import com.example.shop.models.UserVoucher;
import com.example.shop.models.idClass.UserVoucherId;
import com.example.shop.service.interfaces.UserVoucherService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class UserVoucherServiceImpl extends  BaseServiceImpl<UserVoucher, UserVoucherId> implements UserVoucherService {
    public UserVoucherServiceImpl(JpaRepository<UserVoucher, UserVoucherId> repository) {
        super(repository);
    }
}
