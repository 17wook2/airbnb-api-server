package com.example.demo.src.user;



import com.example.demo.config.BaseException;
import com.example.demo.src.user.model.*;
import com.example.demo.utils.JwtService;
import com.example.demo.utils.SHA256;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

// Service Create, Update, Delete 의 로직 처리
@Service
public class UserService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final UserDao userDao;
    private final UserProvider userProvider;
    private final JwtService jwtService;


    @Autowired
    public UserService(UserDao userDao, UserProvider userProvider, JwtService jwtService) {
        this.userDao = userDao;
        this.userProvider = userProvider;
        this.jwtService = jwtService;

    }

    public PostUserRes createUser(PostUserReq postUserReq) throws BaseException {
        if (userProvider.checkEmail(postUserReq.getUserEmail()) == 1){
            throw new BaseException(POST_USERS_EXISTS_EMAIL);
        }
        String pwd;
        try{
            String encryptedPassword = new SHA256().encrypt(postUserReq.getUserPassword());
            postUserReq.setUserPassword(encryptedPassword);
        }catch (Exception exception){
            throw new BaseException(PASSWORD_ENCRYPTION_ERROR);
        }
        try{
            int userId = userDao.createUser(postUserReq);
            String jwt = jwtService.createJwt(userId);
            return new PostUserRes(jwt,userId);

        }catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }


    public int createWishlist(PostWishlistReq postWishlistReq) throws BaseException{
        try{
            return userDao.createWishlist(postWishlistReq);
        }catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public int modifyWishlist(PatchWishlistReq patchWishlistReq) throws BaseException{
        try{
            return userDao.modifyWishlist(patchWishlistReq);
        }catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }

    }

    public int postUserProfile(PostUserProfileReq postUserProfileReq) throws BaseException{
        try{
            return userDao.postUserProfile(postUserProfileReq);
        }catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }

    }
}

