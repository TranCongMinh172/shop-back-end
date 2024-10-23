package com.example.shop.mappers;

import com.example.shop.dtos.requests.VoucherDto;
import com.example.shop.models.Voucher;
import org.springframework.stereotype.Component;

@Component
public class VoucherMapper {
    public Voucher voucherDto2Voucher(VoucherDto voucherDto) {
        return Voucher.builder()
                .voucherType(voucherDto.getVoucherType())
                .discount(voucherDto.getDiscount())
                .expiredDate(voucherDto.getExpiredDate())
                .minAmount(voucherDto.getMinAmount())
                .maxPrice(voucherDto.getMaxPrice())
                .quantity(voucherDto.getQuantity())
                .scope(voucherDto.getScope())
                .build();
    }
}
