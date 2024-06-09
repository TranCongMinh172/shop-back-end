package com.example.shop.mappers;

import com.example.shop.dto.requests.UserVoucherDto;
import com.example.shop.exceptions.DataNotFoundException;
import com.example.shop.models.User;
import com.example.shop.models.UserVoucher;
import com.example.shop.models.Voucher;
import com.example.shop.service.interfaces.UserService;
import com.example.shop.service.interfaces.VoucherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserVoucherMapper {
    private  final UserService userService;
    private final VoucherService voucherService;
    public UserVoucher addUserVoucherDto(UserVoucherDto voucherDto) throws DataNotFoundException {
        User user = userService.findById(voucherDto.getUserId())
                .orElseThrow(()-> new DataNotFoundException("user not found"));
        Voucher voucher = voucherService.findById(voucherDto.getVoucherId())
                .orElseThrow(()-> new DataNotFoundException("voucher not found"));
        return UserVoucher.builder()
                .user(user)
                .voucher(voucher)
                .isVoucherUsed(voucherDto.isUsed())
                .build();
    }
}
