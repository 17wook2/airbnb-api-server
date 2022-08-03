package com.example.demo.src.room;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.room.model.PostRoomReq;
import com.example.demo.src.room.model.PostRoomRes;
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
}
