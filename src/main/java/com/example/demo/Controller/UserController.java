package com.example.demo.Controller;

import com.example.demo.Service.UserService;
import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.dto.User.UserRegisterRequestDto;
import com.example.demo.dto.User.UserRegisterResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @PostMapping("/register")
    public ResponseEntity<UserRegisterResponseDto> register(@RequestBody @Valid final UserRegisterRequestDto userRegisterRequestDto){
            return userService.registerUser(userRegisterRequestDto);
    }


}
