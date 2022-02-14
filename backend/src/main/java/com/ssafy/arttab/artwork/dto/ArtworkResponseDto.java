package com.ssafy.arttab.artwork.dto;

import com.ssafy.arttab.artwork.Artwork;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ArtworkResponseDto { // 작품 정보를 리턴하기 위한 dto

    private Long writerId; // 작가 고유 아이디
    private String writerNickname; // 작가 닉네임
    private String title; // 작품 제목
    private String description; // 작품 내용
    private LocalDateTime regdate; // 작품 등록일
    private String artworkSaveFolder; // 작품 사진 경로
    private String writerProfileSaveFolder; // 작가 프로필 사진 경로
    private String writerEmail; // 작가 이메일
    private int likeNum; // 좋아요 수
    private boolean likeOrNot; // 로그인한 사용자가 작품 좋아요 했는지 여부
    private boolean followOrNot; // 로그인한 사용자가 작가를 팔로우 했는지 여부

    @Builder
    public ArtworkResponseDto(Long writerId, String writerNickname, String title, String description, LocalDateTime regdate, String artworkSaveFolder, String writerProfileSaveFolder, String writerEmail, int likeNum, boolean likeOrNot, boolean followOrNot) {
        this.writerId = writerId;
        this.writerNickname = writerNickname;
        this.title = title;
        this.description = description;
        this.regdate = regdate;
        this.artworkSaveFolder = artworkSaveFolder;
        this.writerProfileSaveFolder = writerProfileSaveFolder;
        this.writerEmail = writerEmail;
        this.likeNum = likeNum;
        this.likeOrNot = likeOrNot;
        this.followOrNot = followOrNot;
    }
}
