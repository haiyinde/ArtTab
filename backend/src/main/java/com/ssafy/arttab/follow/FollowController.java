package com.ssafy.arttab.follow;

import com.ssafy.arttab.follow.dto.FollowSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;
    private final FollowRepository followRepository;

    @PostMapping("/api/v1/follow")
    public ResponseEntity<String> insertFollow(@RequestBody FollowSaveRequestDto requestDto){
        String message = "success";
        try{
            followService.insert(requestDto);
        }catch (Exception e){
            message ="fail";
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @DeleteMapping("/api/v1/follow")
    public ResponseEntity<String> deleteFollow(@RequestBody FollowSaveRequestDto requestDto){
        String message = "success";
        try{
            followService.delete(requestDto);
        }catch (Exception e){
            message = "fail";
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
