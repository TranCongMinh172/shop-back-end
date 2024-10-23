package com.example.shop.controllers;

import com.example.shop.dtos.requests.UserVoucherDto;
import com.example.shop.exceptions.DataNotFoundException;
import com.example.shop.mappers.UserVoucherMapper;
import com.example.shop.models.UserVoucher;
import com.example.shop.models.idClass.UserVoucherId;
import com.example.shop.dtos.requests.responses.ResponseSuccess;
import com.example.shop.service.interfaces.UserVoucherService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/userVoucher")
@RequiredArgsConstructor
public class UserVoucherController {
    private final UserVoucherService userVoucherService;
    private final UserVoucherMapper userVoucherMapper;

    @PostMapping()
    public ResponseSuccess<?> createUserVoucher(@RequestBody @Valid UserVoucherDto voucherDto) throws DataNotFoundException {
        UserVoucher userVoucher = userVoucherMapper.userVoucherDto2UserVoucher(voucherDto);
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "Create a user_voucher successfully",
                userVoucherService.save(userVoucher)
        );
    }

    @PutMapping("/{id}")
    public ResponseSuccess<?> updateUserVoucher(@RequestBody @Valid UserVoucherDto voucherDto) throws DataNotFoundException {
        UserVoucher userVoucher = userVoucherMapper.userVoucherDto2UserVoucher(voucherDto);
        UserVoucherId userVoucherId = new UserVoucherId(voucherDto.getUserId(),voucherDto.getVoucherId());
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "update user_voucher successfully",
                userVoucherService.update(userVoucherId,userVoucher)
        );
    }
}
