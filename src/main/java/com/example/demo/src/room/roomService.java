package com.example.demo.src.room;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.room.domain.Review;
import com.example.demo.src.room.domain.Room;
import com.example.demo.src.room.model.PostRoomReq;
import com.example.demo.src.room.model.PostRoomRes;
import com.example.demo.src.room.model.PostRoomReviewReq;
import com.example.demo.src.user.UserDao;
import com.example.demo.src.user.domain.User;
import com.example.demo.src.user.model.PatchWishlistReq;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class roomService {

    private final roomDao roomDao;
    private final UserDao userDao;
    private final ModelMapper modelMapper;

    public PostRoomRes createRoom(PostRoomReq postRoomReq, String userEmail) throws BaseException{
        try {
            User user = userDao.findByUserEmail(userEmail);
            postRoomReq.setUserId(user.getUserId());
            Room room = modelMapper.map(postRoomReq, Room.class);
            return roomDao.createRoom(room);
        }catch (Exception exception){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

    public Long postRoomReview(PostRoomReviewReq postRoomReviewReq, String userEmail) throws BaseException{
        try {
            User user = userDao.findByUserEmail(userEmail);
            postRoomReviewReq.setGuestId(user.getUserId());
            Review review = modelMapper.map(postRoomReviewReq, Review.class);
            Long result = roomDao.postRoomReview(review);
            return result;
        }catch (Exception e){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

    public int modifyRoomReview(PatchWishlistReq patchWishlistReq) throws BaseException{
        return 0;
    }
}
