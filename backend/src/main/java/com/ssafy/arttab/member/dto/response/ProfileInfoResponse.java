package com.ssafy.arttab.member.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProfileInfoResponse { // 프로필 정보를 리턴하기 위한 dto

    private String nickname; // 프로필 주인의 닉네임
    private String isFollow; // 로그인한 사용자가 프로필 주인을 팔로우하는지 여부
    private int followedNum; // 프로필 주인을 팔로우하는 사람 수
    private int followingNum; // 프로필 주인이 팔로잉하는 사람 수
    private int artworkNum; // 프로필 주인의 작품 수
    private String email; // 프로필 주인의 이메일
    private String profileImageUrl; // 프로필 사진 url
    private String intro; // 프로필 주인의 자기소개

    @Builder
    public ProfileInfoResponse(String nickname, String isFollow, int followedNum, int followingNum, int artworkNum, String email, String profileImageUrl, String intro) {
        this.nickname = nickname;
        this.isFollow = isFollow;
        this.followedNum = followedNum;
        this.followingNum = followingNum;
        this.artworkNum = artworkNum;
        this.email = email;
        this.profileImageUrl = profileImageUrl;
        this.intro = intro;
    }
}
