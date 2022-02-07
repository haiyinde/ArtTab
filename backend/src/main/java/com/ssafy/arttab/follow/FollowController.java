package com.ssafy.arttab.follow;

import com.ssafy.arttab.follow.dto.FollowSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;
    private final FollowRepository followRepository;

    @PostMapping("/api/v1/follow")
    public Long insertFollow(@RequestBody FollowSaveRequestDto requestDto){
        return followService.insert(requestDto);
    }

    @DeleteMapping("/api/v1/follow")
    public void deleteFollow(@RequestBody FollowSaveRequestDto requestDto){
        followService.delete(requestDto);
    }
}
