package com.ssafy.arttab.comment.dto;

import com.ssafy.arttab.artwork.Artwork;
import com.ssafy.arttab.comment.Comment;
import com.ssafy.arttab.member.dto.Memberdto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


/**
 * 댓글을 저장하는 요청 Dto
 */
@Getter
@NoArgsConstructor
public class CommentSaveRequestDto {
    private String content;
    private Long artworkId;
    private Long memberId;

    @Builder
    public CommentSaveRequestDto(String content, Long artworkId, Long memberId) {
        this.content = content;
        this.artworkId = artworkId;
        this.memberId = memberId;
    }

    public Comment toEntity(Artwork artwork, Memberdto memberdto) {
        return Comment.builder()
                .content(content)
                .artwork(artwork)
                .member(memberdto)
                .build();
    }
}
