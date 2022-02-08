package com.ssafy.arttab.comment.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CommentUpdateRequestDto {
    private String content;

    @Builder
    public CommentUpdateRequestDto(String content) { this.content = content; }
}
