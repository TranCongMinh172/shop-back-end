package com.example.shop.service.impls;

import com.example.shop.models.Voucher;
import com.example.shop.service.interfaces.VoucherService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class VoucherServiceImpl extends  BaseServiceImpl<Voucher, Long> implements VoucherService {
    public VoucherServiceImpl(JpaRepository<Voucher, Long> repository) {
        super(repository);
    }
}
