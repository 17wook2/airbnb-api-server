package com.example.demo.src.user.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PatchUserProfileDto {
    private String userEmail;
    private String userName;
    private int userGender;
    private LocalDate userBirthDate;
    private String userAddress;
    private String userProfileImageUrl;
    private String userPhoneNumber;
}
