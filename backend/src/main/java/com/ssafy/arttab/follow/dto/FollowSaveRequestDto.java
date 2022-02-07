package com.ssafy.arttab.follow.dto;

import com.ssafy.arttab.follow.Follow;
import com.ssafy.arttab.member.dto.Memberdto;
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

    public Follow toEntity(Memberdto follower, Memberdto followee){
        return Follow.builder()
                .follower(follower)
                .followee(followee)
                .build();
    }
}
