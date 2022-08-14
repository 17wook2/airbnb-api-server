package com.example.demo.src.room.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostRoomReviewReq {
    private Long guestId;
    private Long roomId;
    private String comment;
    private int cleanliness;
    private int accuracy;
    private int communication;
    private int location;
    private int checkIn;
    private int satisfaction;
}
