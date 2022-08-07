package com.example.demo.src.room;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.room.model.PostRoomReq;
import com.example.demo.src.room.model.PostRoomRes;
import com.example.demo.src.room.model.PostRoomReviewReq;
import com.example.demo.src.user.model.PatchWishlistReq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class roomService {

    private final roomDao roomDao;


    public PostRoomRes createRoom(PostRoomReq postRoomReq) throws BaseException{
        try {
            PostRoomRes postRoomRes = roomDao.createRoom(postRoomReq);
            return postRoomRes;
        }catch (Exception exception){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

    public int postRoomReview(PostRoomReviewReq postRoomReviewReq) throws BaseException{
        try {
            int result = roomDao.postRoomReview(postRoomReviewReq);
            return result;
        }catch (Exception e){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

    public int modifyRoomReview(PatchWishlistReq patchWishlistReq) throws BaseException{
        return 0;
    }
}
