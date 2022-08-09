package com.example.demo.src.user.model;


import lombok.Data;

@Data
public class UserLoginRequestDto {
    private String userEmail;
    private String userPassword;
}
