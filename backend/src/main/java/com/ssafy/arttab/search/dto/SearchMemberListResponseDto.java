package com.ssafy.arttab.search.dto;

import com.ssafy.arttab.artwork.Artwork;
import lombok.Getter;

import java.util.List;

@Getter
public class SearchMemberListResponseDto {
    private String nickname;
    private int artworkCnt;
    private int followerCnt;
    private List<Artwork> artworkList;

    public SearchMemberListResponseDto(String nickname, List<Artwork> artworkList, int artworkCnt, int followerCnt){
        this.nickname = nickname;
        this.artworkList = artworkList;
        this.artworkCnt = artworkCnt;
        this.followerCnt = followerCnt;
    }
}




