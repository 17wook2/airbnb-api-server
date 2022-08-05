package com.example.demo.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostUserProfileReq {
    private int userProfileId;
    private int userId;
    private String userEmail;
    private String userName;
    private int userGender;
    private LocalDate userBirthDate;
    private String userAddress;
    private String userProfileImageUrl;
    private String userPhoneNumber;
    private int host;
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();
}
