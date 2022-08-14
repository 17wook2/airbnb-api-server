package com.example.demo.src.room.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Room {
    private int roomId;
    private int userId;
    private String roomName;
    private String roomDescription;
    private String roomCountry;
    private String roomCity;
    private String roomAddress;
    private int roomBed;
    private int roomBedroom;
    private int roomPrice;
    private int roomMaxcapacity;
    private int roomBathroom;
    private LocalDateTime roomCheckInTime;
    private LocalDateTime roomcCheckOutTime;
    private String roomRefundPolicy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private double latitude;
    private double longitude;

}
