package com.ssafy.arttab.artwork.dto;

import com.ssafy.arttab.artwork.Artwork;
import lombok.Builder;
import lombok.Getter;

@Getter
public class SimpleArtworkDto {

    private Long artworkId; // 작품 아이디
    private String saveFolder; // 작품이 저장된 파일 경로

    @Builder
    public SimpleArtworkDto(Artwork entity){
        artworkId=entity.getId();
        saveFolder="localhost:8080/artworks/"+entity.getSaveFileName();
    }
}
