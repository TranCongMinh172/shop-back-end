package com.example.shop.mappers;

import com.example.shop.dto.requests.VoucherDto;
import com.example.shop.models.Voucher;
import org.springframework.stereotype.Component;

@Component
public class VoucherMapper {
    public Voucher addVoucherDto(VoucherDto voucherDto){
        return Voucher.builder()
                .maxPrice(voucherDto.getMaxPrice())
                .minAmount(voucherDto.getMinPrice())
                .discount(voucherDto.getDiscount())
                .quantity(voucherDto.getQuantity())
                .expiredDate(voucherDto.getExpiredDate())
                .type(voucherDto.getType())
                .scope(voucherDto.getScope())
                .build();
    }
}
