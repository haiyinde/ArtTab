package com.ssafy.arttab.gallery.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class GalleryItemListResponseDto {

    private Long artworkId; // 작품 아이디
    private String saveFileName; // 서버에 저장된 파일 이름
    private String saveFolder; // 서버에 저장된 폴더 경로
    private double xLoc; // 갤러리아이템 x축 위치
    private double yLoc; // 갤러리아이템 y축 위치

    @Builder
    public GalleryItemListResponseDto(Long artworkId, String saveFileName, String saveFolder, double xLoc, double yLoc) {
        this.artworkId = artworkId;
        this.saveFileName = saveFileName;
        this.saveFolder = saveFolder;
        this.xLoc = xLoc;
        this.yLoc = yLoc;
    }

}