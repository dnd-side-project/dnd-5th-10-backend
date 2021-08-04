package com.dnd10.iterview.config.jwt;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class JwtProperties {

    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration-time}")
    private int expirationTime; // 1시간 30분 (1/1000초)
    public static String TOKEN_PREFIX = "Bearer ";
    public static String HEADER_STRING = "Authorization";


}
