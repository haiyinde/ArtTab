package com.ssafy.arttab.gallery.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class GalleryItemSaveRequestDto {

    private Long artworkId; // 작품 아이디

    // 작품 좌표 정보
    private double xLoc;
    private double yLoc;
    private double height;
    private double weight;

    @Builder
    public GalleryItemSaveRequestDto(Long artworkId, double xLoc, double yLoc, double height, double weight) {
        this.artworkId = artworkId;
        this.xLoc = xLoc;
        this.yLoc = yLoc;
        this.height = height;
        this.weight = weight;
    }
}
