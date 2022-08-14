package com.example.demo.src.room;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.reservation.model.GetReservationRes;
import com.example.demo.src.room.domain.Room;
import com.example.demo.src.room.model.*;
import com.example.demo.src.user.auth.Auth;
import com.example.demo.src.user.model.PatchWishlistReq;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class roomController {

    private final roomService roomService;
    private final roomProvider roomProvider;

    @GetMapping("/rooms")
    public BaseResponse<List<Room>> getRooms(@RequestParam(value = "roomType", required = false) String roomType){
        try {
            if (roomType==null) {
                List<Room> getRoomRes = roomProvider.getRooms();
                return new BaseResponse<>(getRoomRes);
            }
                List<Room> getRoomResByType = roomProvider.getRoomsByType(roomType);
                return new BaseResponse<>(getRoomResByType);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @GetMapping("/rooms/{roomId}")
    public BaseResponse<Room> getRoomByRoomId(@PathVariable("roomId") int roomId){
        try {
            Room getRoomRes = roomProvider.getRoomByRoomId(roomId);
            return new BaseResponse<>(getRoomRes);
        }catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }
    @GetMapping("/rooms/{roomId}/reservation")
    public BaseResponse<List<GetReservationRes>> getRoomReservation(@PathVariable("roomId") int roomId){
        try {
            List<GetReservationRes> getRoomReservationRes = roomProvider.getRoomReservation(roomId);
            return new BaseResponse<>(getRoomReservationRes);
        }catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @PostMapping("/rooms")
    public BaseResponse<PostRoomRes> createRoom(@Auth String username, @RequestBody PostRoomReq postRoomReq){
        try {
            PostRoomRes postRoomRes = roomService.createRoom(postRoomReq,username);
            return new BaseResponse<>(postRoomRes);
        }catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @GetMapping("/rooms/{roomId}/images")
    public BaseResponse<List<GetRoomImagesRes>> getRoomImages(@PathVariable("roomId") int roomId){
        try {
            List<GetRoomImagesRes> getRoomImagesRes = roomProvider.getRoomImages(roomId);
            return new BaseResponse<>(getRoomImagesRes);
        }catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @GetMapping("/rooms/{roomId}/reviews")
    public BaseResponse<List<GetRoomReviewRes>>getRoomReview(@PathVariable("roomId") int roomId){
        try {
            List<GetRoomReviewRes>getRoomReviewRes = roomProvider.getRoomReview(roomId);
            return new BaseResponse<>(getRoomReviewRes);
        }catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @PostMapping("/rooms/{roomId}/reviews")
    public BaseResponse<Long> postRoomReview(@Auth String username,
                                                @RequestBody PostRoomReviewReq postRoomReviewReq,
                                                @PathVariable("roomId") Long roomId
                                                ){
        try {
            postRoomReviewReq.setRoomId(roomId);
            Long result = roomService.postRoomReview(postRoomReviewReq,username);
            return new BaseResponse<>(result);
        }catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @PatchMapping("/{userId}/reviews")
    public BaseResponse<Integer> modifyRoomReview(@RequestBody PatchWishlistReq patchWishlistReq,
                                                    @PathVariable("userId") int userId){
        try{
//            patchWishlistReq.setUserId(userId);
            int result = roomService.modifyRoomReview(patchWishlistReq);
            return new BaseResponse<>(result);
        }catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }

    }


}
