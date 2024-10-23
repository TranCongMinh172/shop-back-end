package com.example.shop.service.interfaces;

import com.example.shop.dtos.requests.LoginRequestDto;
import com.example.shop.dtos.requests.ResetPasswordRequest;
import com.example.shop.dtos.requests.UserRegisterDto;
import com.example.shop.dtos.requests.VerifyEmailDto;
import com.example.shop.dtos.responses.LoginResponse;
import com.example.shop.exceptions.DataExistsException;
import com.example.shop.exceptions.DataNotFoundException;
import jakarta.mail.MessagingException;

public interface AuthService {
    void register(UserRegisterDto userRegisterDto) throws DataExistsException, MessagingException;
    LoginResponse login(LoginRequestDto loginRequestDto) throws DataNotFoundException;
    LoginResponse verifyEmail(VerifyEmailDto verifyEmailDto) throws DataNotFoundException;
    LoginResponse refreshToken(String refreshToken) throws DataNotFoundException;
    void sendVerificationEmail(String email) throws DataNotFoundException, MessagingException;
    void verificationEmailForResetPassword(VerifyEmailDto verifyEmailDto) throws DataNotFoundException;
    LoginResponse resetPassword(ResetPasswordRequest resetPasswordRequest) throws DataNotFoundException;
}
