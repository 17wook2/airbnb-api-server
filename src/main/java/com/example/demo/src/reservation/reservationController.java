package com.example.demo.src.reservation;


import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.reservation.model.PostReservationReq;
import com.example.demo.src.reservation.model.PostReservationRes;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/app/reservation")
public class reservationController {

    private final reservationService reservationService;

    @PostMapping("")
    public BaseResponse<PostReservationRes> getRoomReservation(@RequestBody PostReservationReq postReservationReq,
                                                               @RequestParam("roomId") int roomId,
                                                               @RequestParam("guestId") int guestId){
        try {
            postReservationReq.setRoomId(roomId);
            postReservationReq.setGuestId(guestId);
            PostReservationRes postRoomReservationRes = reservationService.createReservation(postReservationReq);
            return new BaseResponse<>(postRoomReservationRes);
        }catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }



}
