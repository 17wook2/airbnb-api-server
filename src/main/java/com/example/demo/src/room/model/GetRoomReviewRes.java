package com.example.demo.src.room.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetRoomReviewRes {
    private int reviewId;
    private int guestId;
    private int roomId;
    private String comment;
    private int cleanliness;
    private int accuracy;
    private int communication;
    private int location;
    private int checkIn;
    private int satisfaction;
    private int status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
