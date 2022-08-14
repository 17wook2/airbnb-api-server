package com.example.demo.src.reservation;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.reservation.model.GetReservationRes;
import com.example.demo.src.reservation.model.PostReservationReq;
import com.example.demo.src.reservation.model.PostReservationRes;
import com.example.demo.src.user.UserDao;
import com.example.demo.src.user.UserProvider;
import com.example.demo.src.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class reservationService {

    private final UserProvider userProvider;
    private final reservationDao reservationDao;
    private final ModelMapper modelMapper;
    public PostReservationRes createReservation(PostReservationReq postReservationReq, String userEmail) throws BaseException {
        try{
            User user = userProvider.getUserByEmail(userEmail);
            postReservationReq.setGuestId(user.getUserId());
            PostReservationRes reservationId = reservationDao.createReservation(postReservationReq);
            return reservationId;
        }catch (Exception exception){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }


    }

    public List<GetReservationRes> getRoomReservationByroomId(Long roomId) throws BaseException{
        try {
            return reservationDao.getRoomReservationByRoomId(roomId);
        } catch (Exception exception){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }

    }

    public PostReservationRes modifyReservation(PostReservationReq postReservationReq, String userEmail) throws BaseException{
        try{
            User user = userProvider.getUserByEmail(userEmail);
            postReservationReq.setGuestId(user.getUserId());
            int result = reservationDao.modifyReservation(postReservationReq);
            return new PostReservationRes(result);
        }catch (Exception exception){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }


    }
}
