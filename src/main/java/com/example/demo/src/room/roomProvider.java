package com.example.demo.src.room;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.room.domain.Room;
import com.example.demo.src.room.model.GetRoomImagesRes;
import com.example.demo.src.reservation.model.GetReservationRes;
import com.example.demo.src.room.model.GetRoomReviewRes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class roomProvider {

    private final roomDao roomDao;
    public List<Room> getRooms() throws BaseException{
        try {
            List<Room> rooms = roomDao.getRooms();
            return rooms;
        }catch (Exception exception){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }

    }

    public Room getRoomByRoomId(int roomId) throws BaseException{
        try {
            Room getRoomRes = roomDao.getRoomById(roomId);
            return getRoomRes;
        }catch (Exception exception){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }

    }

    public List<Room> getRoomsByType(String roomType) throws BaseException{
        try{
            List<Room> getRoomsByTypeRes = roomDao.getRoomsByType(roomType);
            return getRoomsByTypeRes;
        }catch (Exception exception){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

    public List<GetReservationRes> getRoomReservation(int roomId) throws BaseException {
        try{
            List<GetReservationRes> roomReservation = roomDao.getRoomReservation(roomId);
            return roomReservation;
        }catch (Exception exception){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }

    }

    public List<GetRoomImagesRes> getRoomImages(int roomId) throws BaseException {
        try {
            List<GetRoomImagesRes> roomImages = roomDao.getRoomImages(roomId);
            return roomImages;
        } catch (Exception e) {
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

    public List<GetRoomReviewRes> getRoomReview(int roomId) throws  BaseException{
        try {
            List<GetRoomReviewRes> roomReview = roomDao.getRoomReview(roomId);
            return roomReview;
        } catch (Exception e) {
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }
}
