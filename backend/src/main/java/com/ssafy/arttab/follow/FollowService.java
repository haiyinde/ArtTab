package com.ssafy.arttab.follow;

import com.ssafy.arttab.exception.member.NoSuchMemberException;
import com.ssafy.arttab.follow.dto.FollowSaveRequestDto;
import com.ssafy.arttab.follow.dto.FollowingListResponseDto;
import com.ssafy.arttab.member.domain.Member;
import com.ssafy.arttab.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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
    public void insert(FollowSaveRequestDto requestDto) {
        Member follower = memberRepository.findById(requestDto.getFollowerId())
                .orElseThrow(IllegalArgumentException::new);
        Member followee = memberRepository.findById(requestDto.getFolloweeId())
                .orElseThrow(IllegalArgumentException::new);
        var checkFollow = followRepository.checkFollow(follower.getId(), followee.getId())
                .orElseThrow(() -> new
                        IllegalArgumentException("follow 값이 이미 존재합니다."));

        if (checkFollow == 0){
            System.out.println(checkFollow);
            followRepository.save(requestDto.toEntity(follower, followee));
        }
    }

    /***
     *
     * @param requestDto 팔로우를 취소할 회원(followee)과 팔로우를 취소한 회원(follower)의 id가 든 객체
     */
    @Transactional
    public void delete(FollowSaveRequestDto requestDto) {
        Member follower = memberRepository.findById(requestDto.getFollowerId())
                .orElseThrow(() -> new
                        IllegalArgumentException("follower를 찾을 수 없습니다. followerid=" + requestDto.getFollowerId()));
        Member followee = memberRepository.findById(requestDto.getFolloweeId())
                .orElseThrow(() -> new
                        IllegalArgumentException("followee를 찾을 수 없습니다. followeeid=" + requestDto.getFolloweeId()));
        var checkFollow = followRepository.checkFollow(follower.getId(), followee.getId())
                .orElseThrow(() -> new
                        IllegalArgumentException("follow 값이 존재하지 않습니다."));
        if (checkFollow > 0){
            followRepository.unFollow(follower.getId(), followee.getId());
        }
    }

    @Transactional
    public List<FollowingListResponseDto> selectAllFollowing(Long id){
        var list = followRepository.findAllFollowing(id);
        List<FollowingListResponseDto> responseDtos = new ArrayList<>();
        for (Follow follow : list) {
            boolean followState = (followRepository.isFollow(follow.getFollower().getId(), follow.getFollowee().getId()) > 0) ? true : false;
            responseDtos.add(new FollowingListResponseDto(follow.getFollowee(), followState));
        }
        return responseDtos;
    }
}
