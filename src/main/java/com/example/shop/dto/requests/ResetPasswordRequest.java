package com.example.shop.dto.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetPasswordRequest {
    private String email;
    private String password;
    String otpResetPassword;
}
