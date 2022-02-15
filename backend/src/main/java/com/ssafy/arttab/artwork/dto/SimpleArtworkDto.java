package com.ssafy.arttab.artwork.dto;

import com.ssafy.arttab.artwork.Artwork;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;

@Setter
@Getter
@RequiredArgsConstructor
public class SimpleArtworkDto {

    private Long artworkId; // 작품 아이디
    private String saveFolder; // 작품이 저장된 파일 경로

    @Builder
    public SimpleArtworkDto(Long artworkId, String saveFolder) {
        this.artworkId = artworkId;
        this.saveFolder = saveFolder;
    }
}
