package com.ssafy.arttab.like.dto;

import com.ssafy.arttab.artwork.Artwork;
import com.ssafy.arttab.like.Like;
import com.ssafy.arttab.member.domain.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LikeRequestDto {
    private Long artworkId;
    private Long memberId;

    @Builder
    public LikeRequestDto(Long artworkId, Long memberId) {
        this.artworkId = artworkId;
        this.memberId = memberId;
    }

    public Like toEntity(Artwork artwork, Member member) {
        return Like.builder()
                .artwork(artwork)
                .member(member)
                .build();
    }
}
