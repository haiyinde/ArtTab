package com.ssafy.arttab.search;


import com.ssafy.arttab.artwork.Artwork;
import com.ssafy.arttab.artwork.ArtworkRepository;
import com.ssafy.arttab.follow.FollowRepository;
import com.ssafy.arttab.member.dto.Memberdto;
import com.ssafy.arttab.member.repository.MemberRepository;
import com.ssafy.arttab.search.dto.SearchArtworkListResponseDto;
import com.ssafy.arttab.search.dto.SearchMemberListResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class SearchService {

    private final ArtworkRepository artworkRepository;
    private final MemberRepository memberRepository;
    private final FollowRepository followRepository;


    @Transactional
    public List<SearchArtworkListResponseDto> selectArtworkList(String title){
        return artworkRepository.findAllByTitle(title).stream()
                .map(SearchArtworkListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<SearchMemberListResponseDto> selectMemberList(String nickname){
        List<Memberdto> memberdtoList = memberRepository.findAllByNickname(nickname);
        List<SearchMemberListResponseDto> responseDtos = new ArrayList<>();

        for (Memberdto memberdto : memberdtoList) {
            List<Artwork> artworkList = artworkRepository.findAllByMemberId(memberdto.getId()) ;
            int artworkCnt = artworkList.size();
            int followCnt = followRepository.findAllFollowCnt(memberdto.getId());
            responseDtos.add(new SearchMemberListResponseDto(memberdto.getNickname(), artworkList, artworkCnt, followCnt));
        }

        return responseDtos;

    }
}
