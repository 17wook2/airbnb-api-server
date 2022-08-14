package com.example.demo.oAuth.model;

import lombok.Data;

@Data
public class TokenDto {
    private String accessToken;
    private String refreshToken;
}
