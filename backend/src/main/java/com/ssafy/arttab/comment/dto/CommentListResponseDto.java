package com.ssafy.arttab.comment.dto;

import com.ssafy.arttab.comment.Comment;
import com.ssafy.arttab.member.domain.Member;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentListResponseDto {
    private Long id;
    private Long artwork_id;
    private Long member_id;
    private LocalDateTime regdate;
    private String content;
    private String email;
    private String nickName;
    private String imgUrl;

    public CommentListResponseDto(Comment entity, Member member) {
        this.id = entity.getId();
        this.artwork_id = entity.getArtwork().getId();
        this.member_id = entity.getMember().getId();
        this.regdate = LocalDateTime.now();
        this.content = entity.getContent();
        this.email = member.getEmail();
        this.imgUrl = member.getSaveFolder();
        this.nickName = member.getNickname();
    }
}
