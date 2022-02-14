package com.ssafy.arttab.follow;

import com.ssafy.arttab.follow.dto.FollowSaveRequestDto;
import com.ssafy.arttab.follow.dto.FollowingListResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;

    // 내가 팔로우한 회원 정보 리스트
    @GetMapping("/api/v1/following/{id}")
    public ResponseEntity<List<FollowingListResponseDto>> selectAllFollowing(@PathVariable Long id){
        var responseDtos = followService.selectAllFollowing(id);

        if (responseDtos.isEmpty()){
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
        return ResponseEntity.ok().body(responseDtos);
    }

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
