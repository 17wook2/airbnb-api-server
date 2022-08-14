package com.example.demo.Service;

import com.example.demo.Repository.UserRepository;
import com.example.demo.config.BaseException;
import com.example.demo.dto.User.UserRegisterRequestDto;
import com.example.demo.dto.User.UserRegisterResponseDto;
import com.example.demo.error.ErrorCode;
import com.example.demo.error.exception.User.EmailDuplicationException;
import com.example.demo.src.user.domain.User;
import com.example.demo.utils.JwtService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;
import static com.example.demo.config.BaseResponseStatus.POST_USERS_EXISTS_EMAIL;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final JwtService jwtService;

    @Transactional
    public ResponseEntity<UserRegisterResponseDto> registerUser(UserRegisterRequestDto userRegisterRequestDto) {
        if(userRepository.checkEmail((userRegisterRequestDto.getUserEmail())) == 1){
            throw new EmailDuplicationException(ErrorCode.EMAIL_DUPLICATION);
        }
        userRegisterRequestDto.setUserPassword(passwordEncoder.encode(userRegisterRequestDto.getUserPassword()));
        int result = userRepository.registerUser(userRegisterRequestDto);
        String userEmail = userRegisterRequestDto.getUserEmail();
        String jwt = jwtService.createJwt(userEmail);
        UserRegisterResponseDto response = new UserRegisterResponseDto(jwt, userEmail);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
