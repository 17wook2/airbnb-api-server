package com.example.demo.src.room;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.room.model.GetRoomRes;
import com.example.demo.src.room.model.GetRoomReservationRes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class roomProvider {

    private final roomDao roomDao;
    public List<GetRoomRes> getRooms() throws BaseException{
        try {
            List<GetRoomRes> rooms = roomDao.getRooms();
            return rooms;
        }catch (Exception exception){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }

    }

    public GetRoomRes getRoomByRoomId(int roomId) throws BaseException{
        try {
            GetRoomRes getRoomRes = roomDao.getRoomById(roomId);
            return getRoomRes;
        }catch (Exception exception){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }

    }

    public List<GetRoomRes> getRoomsByType(String roomType) throws BaseException{
        try{
            List<GetRoomRes> getRoomRes = roomDao.getRoomsByType(roomType);
            return getRoomRes;
        }catch (Exception exception){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

    public List<GetRoomReservationRes> getRoomReservation(int roomId) throws BaseException {
        try{
            List<GetRoomReservationRes> roomReservation = roomDao.getRoomReservation(roomId);
            return roomReservation;
        }catch (Exception exception){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }

    }
}
