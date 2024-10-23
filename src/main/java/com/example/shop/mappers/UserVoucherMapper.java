package com.example.shop.mappers;


import com.example.shop.dtos.requests.UserVoucherDto;
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
    private final UserService userService;
    private final VoucherService voucherService;

    public UserVoucher userVoucherDto2UserVoucher(UserVoucherDto userVoucherDto) throws DataNotFoundException {
        User user = userService.findById(userVoucherDto.getUserId())
                .orElseThrow(() -> new DataNotFoundException("user not found"));
        Voucher voucher = voucherService.findById(userVoucherDto.getVoucherId())
                .orElseThrow(() -> new DataNotFoundException("voucher not found"));
        UserVoucher userVoucher = new UserVoucher();
        userVoucher.setUsed(false);
        userVoucher.setVoucher(voucher);
        userVoucher.setUser(user);
        return userVoucher;
    }
}
