package com.example.demo.dto.User;

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
