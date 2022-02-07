package com.ssafy.arttab.follow.dto;

import com.ssafy.arttab.follow.Follow;
import com.ssafy.arttab.member.domain.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FollowSaveRequestDto {

    private Long followerId;
    private Long followeeId;

    @Builder
    public FollowSaveRequestDto(Long followerId, Long followeeId) {
        this.followerId = followerId;
        this.followeeId = followeeId;
    }

    public Follow toEntity(Member follower, Member followee){
        return Follow.builder()
                .follower(follower)
                .followee(followee)
                .build();
    }
}
