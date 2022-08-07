package com.example.demo.src.user.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetUserRes {
    private int userId;
    private int status;
    private double latitude;
    private double longitude;
    private String userEmail;
    private String userName;
    private int userGender;
    private LocalDate userBirthDate;
    private String userAddress;
    private String userProfileImageUrl;
    private String userPhoneNumber;
    private int host;
}
