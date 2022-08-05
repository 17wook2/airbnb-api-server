package com.example.demo.src.room.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetRoomReservationRes {
    private int reservationId;
    private int guestId;
    private int roomId;
    private String status;
    private LocalDateTime checkInDate;
    private LocalDateTime checkOutDate;
    private int adultCount;
    private int childCount;
    private int infantCount;
    private int petCount;
}
