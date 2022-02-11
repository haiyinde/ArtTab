package com.ssafy.arttab.comment;

import com.ssafy.arttab.comment.dto.CommentListResponseDto;
import com.ssafy.arttab.comment.dto.CommentSaveRequestDto;
import com.ssafy.arttab.comment.dto.CommentUpdateRequestDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class CommentController {
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";
    private final CommentService commentService;

    @ApiOperation(value = "댓글 전체 조회",
            notes = "작품번호로 댓글 전체 조회 결과를 반환한다.")
    @GetMapping("/api/v1/artwork/{id}/comment")
    public ResponseEntity<List<CommentListResponseDto>> selectAllComment(@PathVariable Long id){
        var commentList = commentService.findAllDesc(id);
         if (commentList.isEmpty()){
             return new ResponseEntity<>(null, HttpStatus.OK);
         }
        return ResponseEntity.ok().body(commentList);
    }

    @ApiOperation(value = "댓글 등록", notes = "DB에 댓글 등록 성공 여부에 따라 success 또는 fail을 반환한다.")
    @PostMapping("/api/v1/artwork/{id}/comment")
    public ResponseEntity<String> insertComment(@PathVariable Long id, @RequestBody CommentSaveRequestDto requestDto){
        try{
            commentService.insert(id, requestDto);
            return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<String>(FAIL, HttpStatus.OK);
        }
    }

    @ApiOperation(value = "댓글 수정", notes = "DB에 댓글 번호로 수정 성공 여부에 따라 success 또는 fail을 반환한다.")
    @PutMapping("/api/v1/comment/{commentId}")
    public ResponseEntity<String> updateComment(@PathVariable Long commentId, @RequestBody CommentUpdateRequestDto requestDto) {
        try {
            commentService.update(commentId, requestDto);
            return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<String>(FAIL, HttpStatus.OK);
        }
    }

    @ApiOperation(value = "댓글 삭제", notes = "DB에 댓글 번호로 ")
    @DeleteMapping("/api/v1/comment/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Long commentId){
        try {
            commentService.delete(commentId);
            return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<String>(FAIL, HttpStatus.OK);
        }
    }

}
