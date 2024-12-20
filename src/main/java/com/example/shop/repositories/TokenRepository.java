package com.example.shop.repositories;

import com.example.shop.models.Token;
import com.example.shop.models.User;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends BaseRepository<Token, Long> {
    List<Token> findAllByUserOrderByExpiredDateDesc(User user);
    boolean existsByAccessToken(String accessToken);
    boolean existsByRefreshToken(String refreshToken);
    Optional<Token> findByRefreshToken(String refreshToken);
    Optional<Token> findByAccessToken(String accessToken);

}