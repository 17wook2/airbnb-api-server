package com.example.demo.dto.User;

import lombok.Data;

@Data
public class UserLoginRequestDto {
    private String userEmail;
    private String userPassword;
}
