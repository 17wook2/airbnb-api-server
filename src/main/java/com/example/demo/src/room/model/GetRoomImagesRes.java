package com.example.demo.src.room.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetRoomImagesRes {
    private int roomImageId;
    private int roomId;
    private String roomImageUrl;
}
