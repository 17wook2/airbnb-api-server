package com.example.demo.Service;

import com.example.demo.Domain.User;
import com.example.demo.Repository.UserRepository;
import com.example.demo.dto.User.UserLoginRequestDto;
import com.example.demo.dto.User.UserLoginResponseDto;
import com.example.demo.dto.User.UserRegisterRequestDto;
import com.example.demo.dto.User.UserRegisterResponseDto;
import com.example.demo.error.ErrorCode;
import com.example.demo.error.exception.User.EmailDuplicationException;
import com.example.demo.error.exception.User.PasswordNotMatchException;
import com.example.demo.error.exception.User.UserNotFoundException;
import com.example.demo.oAuth.ProviderService;
import com.example.demo.oAuth.model.AccessToken;
import com.example.demo.oAuth.model.ProfileDto;
import com.example.demo.utils.JwtService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final JwtService jwtService;
    private final ProviderService providerService;

    @Transactional
    public ResponseEntity<UserRegisterResponseDto> registerUser(UserRegisterRequestDto userRegisterRequestDto) {
        if (userRepository.checkEmail((userRegisterRequestDto.getUserEmail())) == 1) {
            throw new EmailDuplicationException(ErrorCode.EMAIL_DUPLICATION);
        }
        userRegisterRequestDto.setUserPassword(passwordEncoder.encode(userRegisterRequestDto.getUserPassword()));
        long result = userRepository.registerUser(userRegisterRequestDto.getUserEmail(), userRegisterRequestDto.getUserPassword());
        String userEmail = userRegisterRequestDto.getUserEmail();
        String jwt = jwtService.createJwt(userEmail);
        UserRegisterResponseDto response = new UserRegisterResponseDto(jwt, userEmail);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<UserLoginResponseDto> loginUser(UserLoginRequestDto userLoginRequestDto) {
        User user = userRepository.findUserByEmail(userLoginRequestDto.getUserEmail());
        if (user == null) {
            throw new UserNotFoundException(ErrorCode.USER_NOT_FOUND);
        }
        if (!passwordEncoder.matches(userLoginRequestDto.getUserPassword(), user.getUserPassword())) {
            throw new PasswordNotMatchException(ErrorCode.PASSWORD_NOT_MATCH);
        }
        String refreshToken = jwtService.createRefreshToken();
        int result = userRepository.updateRefreshToken(user.getUserId(), refreshToken);
        UserLoginResponseDto response = new UserLoginResponseDto(
                user.getUserId(),
                user.getUserEmail(),
                jwtService.createJwt(user.getUserEmail()),
                refreshToken);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<UserLoginResponseDto> loginByThirdParty(String code, String provider) {
        AccessToken accessToken = providerService.getAccessToken(code, provider);
        ProfileDto profile = providerService.getProfile(accessToken.getAccess_token(), provider);
        String refreshToken = jwtService.createRefreshToken();
        String sessionAccessToken = jwtService.createJwt(profile.getEmail());
        if (userRepository.checkEmail(profile.getEmail()) == 1){
            User user = userRepository.findUserByEmail(profile.getEmail());
            userRepository.updateRefreshToken(user.getUserId(),refreshToken);
            UserLoginResponseDto response = new UserLoginResponseDto(user.getUserId(), user.getUserEmail(), sessionAccessToken, refreshToken);
            return new ResponseEntity<>(response,HttpStatus.OK);
        } else {
            User user = User.builder()
                    .userEmail(profile.getEmail())
                    .provider(provider)
                    .refresh_token(refreshToken)
                    .build();
            userRepository.registerUserFull(user);
            User findUser = userRepository.findUserByEmail(user.getUserEmail());
            UserLoginResponseDto response = new UserLoginResponseDto(findUser.getUserId(), findUser.getUserEmail(), sessionAccessToken, refreshToken);
            return new ResponseEntity<>(response,HttpStatus.OK);
        }
    }
}