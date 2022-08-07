package com.example.demo.src.room;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.room.model.*;
import com.example.demo.src.user.model.PatchWishlistReq;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.yaml.snakeyaml.util.UriEncoder;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/app")
public class roomController {

    private final roomService roomService;
    private final roomProvider roomProvider;

    @GetMapping("/rooms")

    public BaseResponse<List<GetRoomRes>> getRooms(@RequestParam(value = "roomType", required = false) String roomType){
        try {
            if (roomType==null) {
                List<GetRoomRes> getRoomRes = roomProvider.getRooms();
                return new BaseResponse<>(getRoomRes);
            }
                List<GetRoomRes> getRoomResByType = roomProvider.getRoomsByType(roomType);
                return new BaseResponse<>(getRoomResByType);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @GetMapping("/rooms/{roomId}")
    public BaseResponse<GetRoomRes> getRoomByRoomId(@PathVariable("roomId") int roomId){
        try {
            GetRoomRes getRoomRes = roomProvider.getRoomByRoomId(roomId);
            return new BaseResponse<>(getRoomRes);
        }catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }
    @GetMapping("/rooms/{roomId}/reservation")
    public BaseResponse<List<GetRoomReservationRes>> getRoomReservation(@PathVariable("roomId") int roomId){
        try {
            List<GetRoomReservationRes> getRoomReservationRes = roomProvider.getRoomReservation(roomId);
            return new BaseResponse<>(getRoomReservationRes);
        }catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @PostMapping("/rooms")
    public BaseResponse<PostRoomRes> createRoom(@RequestBody PostRoomReq postRoomReq, @RequestParam("userId") int userId){
        try {
            postRoomReq.setUserId(userId);
            PostRoomRes postRoomRes = roomService.createRoom(postRoomReq);
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
    public BaseResponse<Integer> postRoomReview(@RequestBody PostRoomReviewReq postRoomReviewReq,
                                                @PathVariable("roomId") int roomId,
                                                @RequestParam("userId") int userId){
        try {
            postRoomReviewReq.setRoomId(roomId);
            postRoomReviewReq.setGuestId(userId);
            int result = roomService.postRoomReview(postRoomReviewReq);
            return new BaseResponse<>(result);
        }catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @PatchMapping("/{userId}/reviews")
    public BaseResponse<Integer> modifyRoomReview(@RequestBody PatchWishlistReq patchWishlistReq,
                                                    @PathVariable("userId") int userId){
        try{
            patchWishlistReq.setUserId(userId);
            int result = roomService.modifyRoomReview(patchWishlistReq);
            return new BaseResponse<>(result);
        }catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }

    }


}
