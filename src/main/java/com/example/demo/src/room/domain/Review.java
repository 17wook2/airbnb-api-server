package com.example.demo.src.room.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Review {
    private Long reviewId;
    private Long guestId;
    private Long roomId;
    private String comment;
    private int cleanliness;
    private int accuracy;
    private int communication;
    private int location;
    private int checkIn;
    private int satisfaction;
    private int status = 0;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
