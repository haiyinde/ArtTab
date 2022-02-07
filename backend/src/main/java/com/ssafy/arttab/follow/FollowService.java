package com.ssafy.arttab.follow;

import com.ssafy.arttab.follow.dto.FollowSaveRequestDto;
import com.ssafy.arttab.member.dto.Memberdto;
import com.ssafy.arttab.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class FollowService {

    private final FollowRepository followRepository;
    private final MemberRepository memberRepository;

    /***
     *
     * @param requestDto 내가 팔로우 한 사람(followee)의 id, 팔로워가 된 사람(follower)의 id가 든 객체
     * @return 해당 정보를 가지고 있는 Follow 객체의 id값
     */
    @Transactional
    public Long insert(FollowSaveRequestDto requestDto) {
        Memberdto follower = memberRepository.findById(requestDto.getFollowerId())
                .orElseThrow(IllegalArgumentException::new);
        Memberdto followee = memberRepository.findById(requestDto.getFolloweeId())
                .orElseThrow(IllegalArgumentException::new);
        return followRepository.save(requestDto.toEntity(follower, followee)).getId();
    }

    /***
     *
     * @param requestDto 팔로우를 취소할 회원(followee)과 팔로우를 취소한 회원(follower)의 id가 든 객체
     */
    @Transactional
    public void delete(FollowSaveRequestDto requestDto) {
        Memberdto follower = memberRepository.findById(requestDto.getFollowerId())
                .orElseThrow(IllegalArgumentException::new);
        Memberdto followee = memberRepository.findById(requestDto.getFolloweeId())
                .orElseThrow(IllegalArgumentException::new);

        followRepository.unFollow(follower.getId(), followee.getId());
    }
}
