package com.example.demo.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PatchWishlistReq {
    private Long wishlistId;
    private Long userId;
    private String wishlistName;
    private LocalDateTime updatedAt = LocalDateTime.now();
}
