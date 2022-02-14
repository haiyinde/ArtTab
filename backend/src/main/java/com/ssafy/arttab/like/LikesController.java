package com.ssafy.arttab.like;

import com.ssafy.arttab.like.dto.LikeRequestDto;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class LikesController {
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";
    private final LikesService likeService;

    @ApiOperation(value = "좋아요")
    @PostMapping("/api/v1/like")
    public ResponseEntity<String> insertLike(@RequestBody LikeRequestDto requestDto) {
        String message = "success";
        try{
            likeService.InsertLike(requestDto);
        }catch (Exception e){
            message ="fail";
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @DeleteMapping("/api/v1/like")
    public ResponseEntity<String> deleteLike(@RequestBody LikeRequestDto requestDto ) {
        String message = "success";
        try{
            likeService.delete(requestDto);
        }catch (Exception e){
            message ="fail";
        }
        return new ResponseEntity<>(message, HttpStatus.OK);

    }
}
