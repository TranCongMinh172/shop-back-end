package com.example.shop.dtos.requests;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class VerifyEmailDto {
    private String email;
    private String otp;
}
