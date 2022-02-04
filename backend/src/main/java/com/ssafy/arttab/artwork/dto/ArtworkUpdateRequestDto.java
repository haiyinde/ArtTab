package com.ssafy.arttab.artwork.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ArtworkUpdateRequestDto {

    private String title;
    private String desc;
    private String originFileName;
    private String saveFileName;
    private String saveFolder;
    private int size;
    private double width;
    private double height;

    @Builder
    public ArtworkUpdateRequestDto (String title, String desc, String originFileName, String saveFileName,
                                  String saveFolder, int size, double width, double height){
        this.title=title;
        this.desc=desc;
        this.originFileName=originFileName;
        this.saveFileName=saveFileName;
        this.saveFolder=saveFolder;
        this.size=size;
        this.width=width;
        this.height=height;
    }
}