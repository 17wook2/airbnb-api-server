package com.example.demo.src.room;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.room.model.GetRoomRes;
import com.example.demo.src.room.model.PostRoomReq;
import com.example.demo.src.room.model.PostRoomRes;
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


}
