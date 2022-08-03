package com.example.demo.src.room.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.Id;
import javax.validation.constraints.Null;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostRoomReq {

    private int userId;
    private String roomName;
    @Nullable
    private String roomDescription;
    @Nullable
    private String roomCountry;
    @Nullable
    private String roomCity;
    @Nullable
    private String roomAddress;
    @Nullable
    private int roomBed;
    @Nullable
    private int roomBedroom;
    @Nullable
    private int roomPrice;
    @Nullable
    private int roomMaxcapacity;
    @Nullable
    private int roomBathroom;
    @Nullable
    private LocalDateTime roomCheckInTime;
    @Nullable
    private LocalDateTime roomcCheckOutTime;
    @Nullable
    private String roomRefundPolicy;
    @Nullable
    private LocalDateTime createdAt;
    @Nullable
    private LocalDateTime updatedAt;
    @Nullable
    private double latitude;
    @Nullable
    private double longitude;
}
