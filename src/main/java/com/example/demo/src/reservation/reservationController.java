package com.example.demo.src.reservation;


import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.reservation.model.PostReservationReq;
import com.example.demo.src.reservation.model.PostReservationRes;
import com.example.demo.src.reservation.model.GetReservationRes;
import com.example.demo.src.user.auth.Auth;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reservation")
public class reservationController {

    private final reservationService reservationService;

    @GetMapping("")
    public BaseResponse<List<GetReservationRes>> getRoomReservation(@RequestParam("roomId") Long roomId){
        try{
            List<GetReservationRes> reservation = reservationService.getRoomReservationByroomId(roomId);
            return new BaseResponse<>(reservation);
        }catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }

    }
    @PostMapping("")
    public BaseResponse<PostReservationRes> makeRoomReservation(@Auth String userEmail,
                                                                @RequestBody PostReservationReq postReservationReq,
                                                                @RequestParam("roomId") Long roomId){
        try {
            postReservationReq.setRoomId(roomId);
            PostReservationRes postRoomReservationRes = reservationService.createReservation(postReservationReq,userEmail);
            return new BaseResponse<>(postRoomReservationRes);
        }catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @PatchMapping("")
    public BaseResponse<PostReservationRes> modifyRoomReservation(@Auth String userEmail,
                                                                @RequestBody PostReservationReq postReservationReq,
                                                                @RequestParam("roomId") Long roomId){
        try {
            postReservationReq.setRoomId(roomId);
            PostReservationRes postRoomReservationRes = reservationService.modifyReservation(postReservationReq,userEmail);
            return new BaseResponse<>(postRoomReservationRes);
        }catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }


}
