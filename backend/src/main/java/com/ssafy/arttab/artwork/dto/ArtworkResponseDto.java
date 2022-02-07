package com.ssafy.arttab.artwork.dto;

import com.ssafy.arttab.artwork.Artwork;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ArtworkResponseDto {

    private Long writerId;
    private String title;
    private String desc;
    private LocalDateTime regdate;
    private String originFileName;
    private String saveFileName;
    private String saveFolder;

    @Builder
    public ArtworkResponseDto (Artwork entity){
        this.writerId=entity.getWriter().getId();
        this.title=entity.getTitle();
        this.desc=entity.getDesc();
        this.regdate=entity.getRegdate();
        this.originFileName=entity.getOriginFileName();
        this.saveFileName=entity.getSaveFileName();
        this.saveFolder=entity.getSaveFolder();
    }
}
