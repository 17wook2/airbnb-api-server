package com.example.demo.src.user;



import com.example.demo.config.BaseException;
import com.example.demo.src.user.auth.AccessToken;
import com.example.demo.src.user.auth.ProfileDto;
import com.example.demo.src.user.auth.ProviderService;
import com.example.demo.src.user.auth.TokenDto;
import com.example.demo.src.user.model.*;
import com.example.demo.utils.JwtService;
import com.example.demo.utils.SHA256;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

// Service Create, Update, Delete 의 로직 처리
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserDao userDao;
    private final UserProvider userProvider;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final JwtService jwtService;

    private final ProviderService providerService;

    @Transactional
    public UserRegisterResponseDto registerUser(UserRegisterRequestDto userRegisterRequestDto) throws BaseException{
        if(userProvider.checkEmail((userRegisterRequestDto.getUserEmail())) == 1){
            throw new BaseException(POST_USERS_EXISTS_EMAIL);
        }
        try{
            userRegisterRequestDto.setUserPassword(passwordEncoder.encode(userRegisterRequestDto.getUserPassword()));
            User userEntity = modelMapper.map(userRegisterRequestDto, User.class);
            User user = userDao.registerUser(userEntity);
            String userEmail = user.getUserEmail();
            String jwt = jwtService.createJwt(userEmail);
            return new UserRegisterResponseDto(jwt,userEmail);
        }catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    @Transactional
    public UserLoginResponseDto loginUser(UserLoginRequestDto userLoginRequestDto) throws BaseException{
        User user = userDao.findByUserEmail(userLoginRequestDto.getUserEmail());
        if (user == null){
            throw new BaseException(USERS_EMPTY_USER_ID);
        }
        if(!passwordEncoder.matches(userLoginRequestDto.getUserPassword(),user.getUserPassword())){
            throw new BaseException(PASSWORD_ENCRYPTION_ERROR);
        }
        String refreshToken = jwtService.createRefreshToken();
        userDao.updateRefreshToken(user.getUserId(),refreshToken);
        return new UserLoginResponseDto(user.getUserId(),user.getUserEmail(),jwtService.createJwt(user.getUserEmail()), refreshToken);
    }

    @Transactional
    public UserLoginResponseDto loginByThirdParty(String code, String provider) throws BaseException{
        AccessToken accessToken = providerService.getAccessToken(code, provider);
        ProfileDto profile = providerService.getProfile(accessToken.getAccess_token(), provider);
        String refreshToken = jwtService.createRefreshToken();
        String sessionAccessToken = jwtService.createJwt(profile.getEmail());
        if (userProvider.checkEmail(profile.getEmail()) == 1){
            User user = userProvider.getUserByEmail(profile.getEmail());
            user.setRefresh_token(refreshToken);
            userDao.updateRefreshToken(user.getUserId(),refreshToken);
            return new UserLoginResponseDto(user.getUserId(),user.getUserEmail(),sessionAccessToken,user.getRefresh_token());
        }else{
            User user = new User();
            user.setUserEmail(profile.getEmail());
            user.setProvider(provider);
            user.setRefresh_token(refreshToken);
            User registerUser = userDao.registerUser(user);
            return new UserLoginResponseDto(registerUser.getUserId(),registerUser.getUserEmail(),sessionAccessToken,refreshToken);
        }

    }





}

