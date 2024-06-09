package com.example.shop.dto.requests;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;

import java.io.Serializable;

@Getter
@Setter
@Builder
public class UserVoucherDto  {
    private Long voucherId;
    private Long userId;
    private boolean isUsed;
}