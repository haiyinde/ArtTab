package com.ssafy.arttab.search;

import com.ssafy.arttab.artwork.ArtworkRepository;
import com.ssafy.arttab.artwork.ArtworkService;
import com.ssafy.arttab.artwork.dto.ArtworkListResponseDto;
import com.ssafy.arttab.member.repository.MemberRepository;
import com.ssafy.arttab.search.dto.SearchArtworkListResponseDto;
import com.ssafy.arttab.search.dto.SearchMemberListResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;
    private final ArtworkRepository artworkRepository;
    private final MemberRepository memberRepository;


    @GetMapping("/api/v1/searchTitle")
    public List<SearchArtworkListResponseDto> selectAllByArtwork(@RequestParam String title){
        return searchService.selectArtworkList(title);
    }

    @GetMapping("/api/v1/searchNickname")
    public List<SearchMemberListResponseDto> selectAllByMember(@RequestParam String nickname){
        var memberlist = searchService.selectMemberList(nickname);
        return memberlist;
    }
}
