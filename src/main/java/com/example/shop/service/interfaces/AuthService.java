package com.example.shop.service.interfaces;

import com.example.shop.dto.requests.LoginRequestDto;
import com.example.shop.dto.requests.ResetPasswordRequest;
import com.example.shop.dto.requests.UserRegisterDto;
import com.example.shop.dto.requests.VerifyEmailDto;
import com.example.shop.dto.responses.LoginResponse;
import com.example.shop.exceptions.DataExistsException;
import com.example.shop.exceptions.DataNotFoundException;
import jakarta.mail.MessagingException;

public interface AuthService {
    void register(UserRegisterDto userRegisterDto) throws DataExistsException, MessagingException;
    LoginResponse login(LoginRequestDto loginRequestDto) throws DataNotFoundException;
    LoginResponse verifyEmail(VerifyEmailDto verifyEmailDto) throws DataNotFoundException;
    void sendVerificationEmail(String email) throws DataNotFoundException, MessagingException;
    void verificationEmailForResetPassword(VerifyEmailDto verifyEmailDto) throws DataNotFoundException;
    LoginResponse resetPassword(ResetPasswordRequest resetPasswordRequest) throws DataNotFoundException;
}
