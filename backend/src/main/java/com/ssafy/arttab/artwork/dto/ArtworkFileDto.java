package com.ssafy.arttab.artwork.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ArtworkFileDto { // Artwork에 저장할 정보를 담는 Dto

    private Long writerId; // 작성자 아이디
    private String title; // 작품이름
    private String description; // 작품설명
    private String originFileName; // 원본 파일 이름
    private String saveFileName; // 서버에 저장된 파일 이름
    private String saveFolder; // 저장된 폴더 경로

    @Builder
    public ArtworkFileDto(Long writerId, String title, String description, String originFileName,
                          String saveFileName, String saveFolder){
        this.writerId=writerId;
        this.title=title;
        this.description=description;
        this.originFileName=originFileName;
        this.saveFileName=saveFileName;
        this.saveFolder="file:///" + saveFolder;
    }
}
