package com.example.demo.src.reservation;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.reservation.model.PostReservationReq;
import com.example.demo.src.reservation.model.PostReservationRes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class reservationService {

    private final reservationDao reservationDao;
    public PostReservationRes createReservation(PostReservationReq postReservationReq) throws BaseException {
        try{
            PostReservationRes reservationId = reservationDao.createReservation(postReservationReq);
            return reservationId;
        }catch (Exception exception){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }


    }
}
