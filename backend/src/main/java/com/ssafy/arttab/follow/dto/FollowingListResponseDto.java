package com.ssafy.arttab.follow.dto;

import com.ssafy.arttab.follow.Follow;
import com.ssafy.arttab.member.domain.Member;
import lombok.Getter;

@Getter
public class FollowingListResponseDto {
    private String nickName;
    private String imgUrl;
    private Long memberId;
    private String email;
    private boolean followState;

    public FollowingListResponseDto(Member member, boolean followState){
        this.memberId = member.getId();
        this.nickName = member.getNickname();
        this.imgUrl = member.getSaveFolder();
        this.email = member.getEmail();
        this.followState = followState;

    }
}
