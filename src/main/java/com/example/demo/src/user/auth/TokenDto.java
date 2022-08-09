package com.example.demo.src.user.auth;

import lombok.Data;

@Data
public class TokenDto {
    private String accessToken;
    private String refreshToken;
}
