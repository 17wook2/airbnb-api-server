package com.example.demo.Controller;

import com.example.demo.Service.UserService;
import com.example.demo.dto.User.UserLoginRequestDto;
import com.example.demo.dto.User.UserLoginResponseDto;
import com.example.demo.dto.User.UserRegisterRequestDto;
import com.example.demo.dto.User.UserRegisterResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @PostMapping("/register")
    public ResponseEntity<UserRegisterResponseDto> register(@RequestBody @Valid final UserRegisterRequestDto userRegisterRequestDto){
            return userService.registerUser(userRegisterRequestDto);
    }

    /**
     * 이메일 로그인 API
     * [POST] /login
     * @return BaseResponse<UserRegisterResponseDto>
     */
    @PostMapping("/login")
    public ResponseEntity<UserLoginResponseDto> login(@RequestBody UserLoginRequestDto userLoginRequestDto){
            return userService.loginUser(userLoginRequestDto);
    }

    @PostMapping("/login/{provider}")
    public ResponseEntity<UserLoginResponseDto> loginByThirdParty(@RequestParam String code, @PathVariable String provider){
        return userService.loginByThirdParty(code,provider);
    }

}
