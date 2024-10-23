package com.example.shop.dtos.requests;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserVoucherDto  {
    private Long voucherId;
    private Long userId;
    private boolean isUsed;
}