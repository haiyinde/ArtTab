package com.ssafy.arttab.search;

import com.ssafy.arttab.artwork.ArtworkRepository;
import com.ssafy.arttab.member.repository.MemberRepository;
import com.ssafy.arttab.search.dto.SearchArtworkListResponseDto;
import com.ssafy.arttab.search.dto.SearchMemberListResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<SearchArtworkListResponseDto>> selectAllByArtwork(@RequestParam String title,
                                                                                 @RequestParam Long id){
        var artworkList = searchService.selectArtworkList(title, id);
        if (artworkList.isEmpty()){
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
        return ResponseEntity.ok().body(artworkList);
    }

    @GetMapping("/api/v1/searchNickname")
    public List<SearchMemberListResponseDto> selectAllByMember(@RequestParam String nickname){
        var memberlist = searchService.selectMemberList(nickname);
        return memberlist;
    }
}
