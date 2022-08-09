package com.example.demo.src.user;

import com.example.demo.src.user.auth.ProviderService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.user.model.*;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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


    @PostMapping("/register")
    public BaseResponse<UserRegisterResponseDto> register(@RequestBody UserRegisterRequestDto userRegisterRequestDto){
        try{
            UserRegisterResponseDto userRegisterResponseDto = userService.registerUser(userRegisterRequestDto);
            return new BaseResponse<>(userRegisterResponseDto);
        }catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @PostMapping("/login")
    public BaseResponse<UserLoginResponseDto> login(@RequestBody UserLoginRequestDto userLoginRequestDto){
        try{
            UserLoginResponseDto userLoginResponseDto = userService.loginUser(userLoginRequestDto);
            return new BaseResponse<>(userLoginResponseDto);
        }catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @PostMapping("/login/{provider}")
    public BaseResponse<UserLoginResponseDto> loginByThirdParty(@RequestParam String code, @PathVariable String provider){
        try{
            UserLoginResponseDto userLoginResponseDto = userService.loginByThirdParty(code, provider);
            return new BaseResponse<>(userLoginResponseDto);
        }catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }


//    @ResponseBody
//    @GetMapping("") // (GET) 127.0.0.1:9000/app/users
//    public BaseResponse<List<GetUserRes>> getUsers() {
//        try{
//            // Get Users
//            List<GetUserRes> getUsersRes = userProvider.getUsers();
//            return new BaseResponse<>(getUsersRes);
//        } catch(BaseException exception){
//            return new BaseResponse<>(exception.getStatus());
//    }
//    }
//
//    @GetMapping("/{userId}")
//    public BaseResponse<GetUserRes> getUserByuserId(@PathVariable("userId") int userId){
//        try {
//            GetUserRes getUserRes = userProvider.getUser(userId);
//            return new BaseResponse<>(getUserRes);
//        } catch (BaseException exception) {
//            return new BaseResponse<>((exception.getStatus()));
//        }
//    }
//
//    @PostMapping("/user-profile")
//    public BaseResponse<Integer> postUserProfile(@RequestBody PostUserProfileReq postUserProfileReq){
//        try {
//            int result = userService.postUserProfile(postUserProfileReq);
//            return new BaseResponse<>(result);
//        } catch (BaseException exception) {
//            return new BaseResponse<>((exception.getStatus()));
//        }
//    }
//    @PostMapping("/sign-up")
//    public BaseResponse<PostUserRes> createUser(@Validated @RequestBody PostUserReq postUserReq){
//        try{
//            PostUserRes postUserRes = userService.createUser(postUserReq);
//            return new BaseResponse<>(postUserRes);
//        } catch (BaseException exception) {
//            return new BaseResponse<>(exception.getStatus());
//        }
//
//    }
//
//    @GetMapping("/{userId}/wishlists")
//    public BaseResponse<List<GetWishlistRes>> getUserWishlists(@PathVariable("userId") int userId){
//        try{
//            List<GetWishlistRes> wishlists = userProvider.getWishlists(userId);
//            return new BaseResponse<>(wishlists);
//        }catch (BaseException exception){
//            return new BaseResponse<>(exception.getStatus());
//        }
//    }
//
//    @PostMapping("/{userId}/wishlists")
//    public BaseResponse<Integer> createWishlist(@RequestBody PostWishlistReq postWishlistReq,
//                                                        @PathVariable("userId") int userId){
//        try{
//            postWishlistReq.setUserId(userId);
//            int result = userService.createWishlist(postWishlistReq);
//            return new BaseResponse<>(result);
//        }catch (BaseException exception){
//            return new BaseResponse<>(exception.getStatus());
//        }
//    }
//
//    @PatchMapping("/{userId}/wishlists")
//    public BaseResponse<Integer> modifyWishlistName(@RequestBody PatchWishlistReq patchWishlistReq,
//                                                   @PathVariable("userId") int userId){
//        try{
//            patchWishlistReq.setUserId(userId);
//            int result = userService.modifyWishlist(patchWishlistReq);
//            return new BaseResponse<>(result);
//        }catch (BaseException exception){
//            return new BaseResponse<>(exception.getStatus());
//        }
//
//    }
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
