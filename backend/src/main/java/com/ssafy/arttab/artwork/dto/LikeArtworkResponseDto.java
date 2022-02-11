package com.ssafy.arttab.artwork.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class LikeArtworkResponseDto implements Comparable<LikeArtworkResponseDto>{ // 좋아하는 작품 리스트

    private String artworkTitle; // 작품 제목
    private String memberNickname; // 회원 닉네임
    private String saveFolder; // 작품 경로
    private boolean likeOrNot; // 작품을 좋아요 했는지 여부
    private Long artworkId; // 작품 아이디
    private Long memberId; // 회원 아이디
    private LocalDateTime regdate; // 등록일

    @Builder
    public LikeArtworkResponseDto(String artworkTitle, String memberNickname, String saveFolder, boolean likeOrNot, Long artworkId, Long memberId, LocalDateTime regdate) {
        this.artworkTitle = artworkTitle;
        this.memberNickname = memberNickname;
        this.saveFolder = saveFolder;
        this.likeOrNot = likeOrNot;
        this.artworkId = artworkId;
        this.memberId = memberId;
        this.regdate = regdate;
    }

    @Override
    public int compareTo(LikeArtworkResponseDto o) {
        if(this.regdate.isAfter(o.getRegdate())) return -1;
        else return 1;
    }

}
