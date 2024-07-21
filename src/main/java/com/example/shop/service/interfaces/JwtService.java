package com.example.shop.service.interfaces;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;


public interface JwtService {
    String generateJwtToken(UserDetails userDetails);
    String extractEmail(String token);
    boolean validateToken(String token, UserDetails userDetails);
    String generateRefreshToken(Map<String, Object> claims, UserDetails userDetails);
    boolean validateRefreshToken(String refreshToken, UserDetails userDetails);
}
