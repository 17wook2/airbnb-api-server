package com.example.demo.Domain;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    private Long userId;
    private String userPassword;
    private int status;
    private String userEmail;
    private String userPhoneNumber;
    private String role;
    private int enabled;
    private String provider;
    private String refresh_token;
    private String userName;
    private int userGender;
    private LocalDate userBirthDate;
    private String userAddress;
    private String userProfileImageUrl;
    private int host;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
