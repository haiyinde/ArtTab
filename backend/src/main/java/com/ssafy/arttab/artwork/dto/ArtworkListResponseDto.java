package com.ssafy.arttab.artwork.dto;

import com.ssafy.arttab.artwork.Artwork;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class ArtworkListResponseDto {

    private Long memberId;
    private String memberNickname;
    private Long artworkId; // 작품 식별번호
    private String artworkTitle; // 작품 제목
    private LocalDateTime artworkRegdate; // 작성일
    private String saveFileName; // 서버에 저장된 파일 이름
    private String saveFolder; // 저장된 폴더 경로
    private String imageUrl; // 이미지 url

    @Builder
    public ArtworkListResponseDto(Artwork entity){
        this.memberId=entity.getWriter().getId();
        this.memberNickname=entity.getWriter().getNickname();
        this.artworkId=entity.getId();
        this.artworkTitle=entity.getTitle();
        this.artworkRegdate=entity.getRegdate();
        this.saveFileName=entity.getSaveFileName();
        this.saveFolder=entity.getSaveFolder();
        this.imageUrl="localhost:8080/artworks/"+entity.getSaveFileName();
    }
}
