package com.example.demo.src.user.model;

import lombok.Data;

@Data
public class UserRegisterRequestDto {
    private String userEmail;
    private String userPassword;
}