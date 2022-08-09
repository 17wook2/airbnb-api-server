package com.example.demo.src.room.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Enumerated;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetRoomImagesRes {
    private int roomImageId;
    private int roomId;
    private String roomImageUrl;

}
