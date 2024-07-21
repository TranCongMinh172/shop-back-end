package com.example.shop.service.impls;

import com.example.shop.repositories.TokenRepository;
import com.example.shop.service.interfaces.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.websocket.Decoder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {
    @Value("${jwt.expiryDate}")
    private long expiryDate;
    @Value("${jwt.secret}")
    private String secret;
    private final TokenRepository tokenRepository;

    @Override
    public String generateJwtToken(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + (1000 * expiryDate)))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public String generateRefreshToken(Map<String, Object> claims, UserDetails userDetails) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + (1000 * (86400 * 14))))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }
    @Override
    public boolean validateRefreshToken(String refreshToken, UserDetails userDetails) {
        boolean existByRefreshToken = tokenRepository.existsByRefreshToken(refreshToken);
        final String username = extractClaim(refreshToken, Claims::getSubject);
        return username.equals(userDetails.getUsername()) && expiredDateToken(refreshToken) && existByRefreshToken;
    }

    @Override
    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    @Override
    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractClaim(token, Claims::getSubject);
        return  username.equals(userDetails.getUsername()) && !expiredDateToken(token);
    }
    private boolean expiredDateToken(String token){
        return extractClaim(token, Claims :: getExpiration).before(new Date());
    }

    private Key getSignKey(){
        byte[] keys = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keys);
    }
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String token){
        return Jwts.parserBuilder().setSigningKey(getSignKey())
                .build().parseClaimsJws(token).getBody();
    }
}
