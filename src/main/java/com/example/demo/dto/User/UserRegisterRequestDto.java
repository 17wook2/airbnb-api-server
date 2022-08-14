package com.example.demo.dto.User;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
public class UserRegisterRequestDto {

    @NotNull
    @Email
    private String userEmail;

    @NotNull
    private String userPassword;
}
