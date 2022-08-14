package com.example.demo.src.user;

import com.example.demo.src.user.auth.Auth;
import com.example.demo.src.user.auth.AuthUser;
import com.example.demo.src.user.auth.ProviderService;
import com.example.demo.src.user.domain.UserProfile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.user.model.*;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


import static com.example.demo.config.BaseResponseStatus.*;
import static com.example.demo.utils.ValidationRegex.isRegexEmail;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserProvider userProvider;
    private final UserService userService;
    private final JwtService jwtService;


    /**
     * 회원 가입 API
     * [POST] /register
     * @return BaseResponse<UserRegisterResponseDto>
     */
    @PostMapping("/register")
    public BaseResponse<UserRegisterResponseDto> register(@RequestBody UserRegisterRequestDto userRegisterRequestDto){
        try{
            UserRegisterResponseDto userRegisterResponseDto = userService.registerUser(userRegisterRequestDto);
            return new BaseResponse<>(userRegisterResponseDto);
        }catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 이메일 로그인 API
     * [POST] /login
     * @return BaseResponse<UserRegisterResponseDto>
     */
    @PostMapping("/login")
    public BaseResponse<UserLoginResponseDto> login(@RequestBody UserLoginRequestDto userLoginRequestDto){
        try{
            UserLoginResponseDto userLoginResponseDto = userService.loginUser(userLoginRequestDto);
            return new BaseResponse<>(userLoginResponseDto);
        }catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 소셜 로그인 API
     * [POST] /login
     * @return BaseResponse<UserRegisterResponseDto>
     */
    @PostMapping("/login/{provider}")
    public BaseResponse<UserLoginResponseDto> loginByThirdParty(@RequestParam String code, @PathVariable String provider){
        try{
            UserLoginResponseDto userLoginResponseDto = userService.loginByThirdParty(code, provider);
            return new BaseResponse<>(userLoginResponseDto);
        }catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 회원 프로필 입력 API
     * [POST] /account-settings
     * @return BaseResponse<PostUserProfileRes>
     */
    @PostMapping("/account-settings")
    public BaseResponse<PostUserProfileRes> InsertUserInfo(@Auth String username, @RequestBody PostUserProfileReq postUserProfileReq){
        try{
            PostUserProfileRes postUserProfileRes = userService.InsertUserProfile(postUserProfileReq, username);
            return new BaseResponse<>(postUserProfileRes);
        }catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }

    }

    /**
     * 회원 프로필 받기 API
     * [GET] /account-settings
     * @return BaseResponse<GetUserProfileRes>
     */
    @GetMapping("/account-settings")
    public BaseResponse<UserProfile> GetUserProfile(@Auth String userEmail){
        try{
            UserProfile userProfile = userService.GetUserProfile(userEmail);
            return new BaseResponse<>(userProfile);
        }catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }

    }
    @GetMapping("wishlists")
    public BaseResponse<List<GetWishlistRes>> getUserWishlists(@Auth String userEmail){
        try{
            List<GetWishlistRes> wishlists = userProvider.getWishlists(userEmail);
            return new BaseResponse<>(wishlists);
        }catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @PostMapping("wishlists")
    public BaseResponse<Long> createWishlist(@Auth String userEmail,
                                             @RequestBody PostWishlistReq postWishlistReq){
        try{
            Long result = userService.createWishlist(postWishlistReq,userEmail);
            return new BaseResponse<>(result);
        }catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @PatchMapping("wishlists")
    public BaseResponse<Integer> modifyWishlist(@Auth String userEmail,
                                                    @RequestBody PatchWishlistReq patchWishlistReq
                                                   ){
        try{
            int result = userService.modifyWishlist(patchWishlistReq,userEmail);
            return new BaseResponse<>(result);
        }catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }

    }
//
//    @GetMapping("/{userId}/reviews")
//    public BaseResponse<List<GetUserReviewRes>> getUserReviews(@PathVariable("userId") int userId){
//        try{
//            List<GetUserReviewRes> wishlists = userProvider.getReviews(userId);
//            return new BaseResponse<>(wishlists);
//        }catch (BaseException exception){
//            return new BaseResponse<>(exception.getStatus());
//        }
//    }


}
