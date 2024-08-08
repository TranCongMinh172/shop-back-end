package com.example.shop.service.impls;

import com.example.shop.models.Token;
import com.example.shop.models.User;
import com.example.shop.repositories.TokenRepository;
import com.example.shop.service.interfaces.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {
    private final TokenRepository tokenRepository;
    @Override
    public void saveToken(User user, Token token) {
        List<Token> tokens = tokenRepository.findAllByUserOrderByExpiredDateDesc(user);
        if(!tokens.isEmpty() && tokens.size()>=2){
            Token tokenDelete = tokens.get(tokens.size()-1);
            tokenRepository.delete(tokenDelete);
        }
        tokenRepository.save(token);
    }
}
