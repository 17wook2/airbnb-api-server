package com.example.demo.src.user.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class PostUserReq {

    private String userPassword;
    private String userEmail;
    private String userName;
    private int userGender;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate userBirthDate;

    private String userAddress;

    private String userProfileImageUrl;

    private String userPhoneNumber;

    private int host;
}
