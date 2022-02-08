package com.ssafy.arttab.artwork.dto;

import lombok.*;

@Getter
@NoArgsConstructor
public class ArtworkUpdateRequestDto {

    private String title;
    private String desc;
    private String originFileName;
    private String saveFileName;
    private String saveFolder;

    @Builder
    public ArtworkUpdateRequestDto (String title, String desc, String originFileName, String saveFileName,
                                    String saveFolder, int size, double width, double height){
        this.title=title;
        this.desc=desc;
        this.originFileName=originFileName;
        this.saveFileName=saveFileName;
        this.saveFolder=saveFolder;
    }
}