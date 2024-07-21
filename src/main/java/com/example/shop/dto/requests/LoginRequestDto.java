package com.example.shop.dto.requests;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LoginRequestDto {
    private String email;
    private String password;
}
