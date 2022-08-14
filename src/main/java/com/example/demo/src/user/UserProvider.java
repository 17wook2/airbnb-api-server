package com.example.demo.src.user;


import com.example.demo.config.BaseException;
import com.example.demo.src.user.domain.User;
import com.example.demo.src.user.model.GetWishlistRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

//Provider : Read의 비즈니스 로직 처리
@Service
public class UserProvider {

    private final UserDao userDao;
    private final JwtService jwtService;


    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public UserProvider(UserDao userDao, JwtService jwtService) {
        this.userDao = userDao;
        this.jwtService = jwtService;
    }

    public int checkEmail(String email) throws BaseException {
        try{
            return userDao.checkEmail(email);
        }catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }

    }

    public User getUserByEmail(String email) throws BaseException{
        try{
            return userDao.findByUserEmail(email);
        }catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetWishlistRes> getWishlists(String userEmail) throws BaseException {
        try{
            User user = getUserByEmail(userEmail);
            List<GetWishlistRes> wishlists = userDao.getWishlists(user.getUserId());
            return wishlists;
        }catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }

    }

//    public GetUserRes getUser(int userId) throws BaseException {
//        try {
//            GetUserRes getUserRes = userDao.getUser(userId);
//            return getUserRes;
//        } catch (Exception exception) {
//            throw new BaseException(DATABASE_ERROR);
//        }
//
//    }
//
//    public List<GetUserRes> getUsers() throws BaseException {
//        try {
//            List<GetUserRes> getUserRes = userDao.getUsers();
//            return getUserRes;
//        } catch (Exception exception) {
//            throw new BaseException(DATABASE_ERROR);
//        }
//    }
//
//
//
//

//
//    public List<GetUserReviewRes> getReviews(int userId) throws BaseException{
//        try{
//            List<GetUserReviewRes> reviews = userDao.getReviews(userId);
//            return reviews;
//        }catch (Exception e){
//            throw new BaseException(DATABASE_ERROR);
//        }
//    }
//
//    public int getUserEmail(String userEmail) {
//        Long  = userDao.checkEmail(userEmail);
//
//    }
}
