package com.example.demo.src.user.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProfile {
    private int userProfileId;
    private int userId;
    private String userName;
    private int userGender;
    private LocalDate userBirthDate;
    private String userAddress;
    private String userProfileImageUrl;
    private String userPhoneNumber;
    private int host = 0;
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();
}
