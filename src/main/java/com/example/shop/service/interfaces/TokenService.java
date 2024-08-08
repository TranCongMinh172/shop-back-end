package com.example.shop.service.interfaces;

import com.example.shop.models.Token;
import com.example.shop.models.User;

public interface TokenService {
    void saveToken(User user, Token token);
}
