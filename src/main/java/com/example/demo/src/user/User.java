package com.example.demo.src.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Long userId;
    private String userPassword;
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();
    private int status = 0;
    private String userEmail;
    private String userPhoneNumber;
    private String role = "ROLE_MEMBER";
    private int enabled = 0;
    private String provider = null;
    private String refresh_token = null;
}
