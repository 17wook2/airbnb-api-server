package com.example.demo.src.user.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class PostUserReq {

    @NotBlank(message = "PASSWORD_IS_MANDATORY")
    private String userPassword;

    @Email(message = "NOT_VALID_EMAIL")
    private String userEmail;

    @NotBlank(message = "NAME_IS_MANDATORY")
    private String userName;

    private int userGender;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDateTime userBirthDate;

    private String userAddress;

    private String userProfileImageUrl;

    private String userPhoneNumber;

    private int host;
}
