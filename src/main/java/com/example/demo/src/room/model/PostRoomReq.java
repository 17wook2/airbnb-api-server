package com.example.demo.src.room.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;

import javax.persistence.Id;
import javax.validation.constraints.Null;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostRoomReq {
    private Long userId;
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
    @DateTimeFormat(pattern = "HH:mm:ss")
    private LocalTime roomCheckInTime;
    @DateTimeFormat(pattern = "HH:mm:ss")
    private LocalTime roomcCheckOutTime;
    private String roomRefundPolicy;
    private double latitude;
    private double longitude;
}
