package com.ssafy.arttab.comment;

import com.ssafy.arttab.comment.dto.CommentListResponseDto;
import com.ssafy.arttab.comment.dto.CommentSaveRequestDto;
import com.ssafy.arttab.comment.dto.CommentUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class CommentController {
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";
    private final CommentService commentService;


    @GetMapping("/api/v1/artwork/{id}/comment")
    public List<CommentListResponseDto> selectAllComment(@PathVariable Long id){
        return commentService.findAllDesc(id);
    }

    @PostMapping("/api/v1/artwork/{id}/comment")
    public Long insertComment(@PathVariable Long id, @RequestBody CommentSaveRequestDto requestDto){
        return commentService.insert(id, requestDto);
    }

    @PutMapping("/api/v1/artwork/comment/{commentId}")
    public Long updateComment(@PathVariable Long commentId, @RequestBody CommentUpdateRequestDto requestDto) {
        return commentService.update(commentId, requestDto);
    }

    @DeleteMapping("/api/v1/artwork/{id}/comment/{commentId}")
    public Long deleteComment(@PathVariable Long commentId){
        commentService.delete(commentId);
        return commentId;
    }

}
