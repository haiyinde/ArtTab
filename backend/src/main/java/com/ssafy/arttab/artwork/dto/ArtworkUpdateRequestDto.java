package com.ssafy.arttab.artwork.dto;

import lombok.*;

@Getter
@NoArgsConstructor
public class ArtworkUpdateRequestDto {

    private String title;
    private String description;
    private String originFileName;
    private String saveFileName;
    private String saveFolder;

    @Builder
    public ArtworkUpdateRequestDto (String title, String description, String originFileName, String saveFileName,
                                    String saveFolder, int size, double width, double height){
        this.title=title;
        this.description=description;
        this.originFileName=originFileName;
        this.saveFileName=saveFileName;
        this.saveFolder=saveFolder;
    }
}