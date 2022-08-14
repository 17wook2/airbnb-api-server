package com.example.demo.dto.User;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserRegisterResponseDto {
    private String jwt;
    private String userEmail;
}
