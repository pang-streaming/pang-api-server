package com.pangapiserver.infrastructure.security.token;

import com.pangapiserver.domain.user.entity.UserEntity;
import com.pangapiserver.infrastructure.security.properties.TokenProperties;
import com.pangapiserver.infrastructure.security.token.enumeration.TokenType;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

import static java.lang.System.currentTimeMillis;

@Component
@RequiredArgsConstructor
public class TokenProvider {
    private final TokenProperties properties;

    public String generate(TokenType tokenType, UserEntity user) {
        return Jwts.builder()
            .claim("category", tokenType.name())
            .claim("email", user.getEmail())
            .claim("role", user.getRoles())
            .claim("username", user.getUsername())
            .issuedAt(new Date(currentTimeMillis()))
            .expiration(new Date(currentTimeMillis() + getTokenExpire(tokenType)))
            .signWith(properties.getSecretKey())
            .compact();
    }

    private long getTokenExpire(TokenType tokenType) {
        if (tokenType == TokenType.ACCESS) return properties.getAccessToken();
        return properties.getRefreshToken();
    }
}
