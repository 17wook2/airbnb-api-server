package com.example.demo.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserLoginResponseDto {
    private Long userId;
    private String userEmail;
    private String accessToken;
    private String refreshToken;
}
