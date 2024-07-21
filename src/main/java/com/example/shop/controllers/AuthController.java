package com.example.shop.controllers;

import com.example.shop.dto.requests.LoginRequestDto;
import com.example.shop.dto.requests.UserRegisterDto;
import com.example.shop.dto.requests.VerifyEmailDto;
import com.example.shop.exceptions.DataNotFoundException;
import com.example.shop.service.interfaces.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.shop.dto.requests.responses.ResponseSuccess;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseSuccess<?> login(@RequestBody LoginRequestDto loginRequestDto) throws DataNotFoundException {
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
               "login successfully",
                authService.login(loginRequestDto)
        );
    }
    @PostMapping("/register")
    public ResponseSuccess<?> register(@RequestBody UserRegisterDto userRegisterDto) throws Exception {
        authService.register(userRegisterDto);
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "register successfully",
                "check otp in your email"

        );
    }
    @PostMapping("/verify-email")
    public ResponseSuccess<?> verifyEmail(@RequestBody VerifyEmailDto verifyEmailDto) throws Exception {
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "verify email successfully",
                authService.verifyEmail(verifyEmailDto)
        );
    }
}
