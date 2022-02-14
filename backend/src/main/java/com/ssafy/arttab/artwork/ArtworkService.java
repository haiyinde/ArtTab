package com.ssafy.arttab.artwork;

import com.ssafy.arttab.artwork.dto.*;
import com.ssafy.arttab.follow.Follow;
import com.ssafy.arttab.follow.FollowRepository;
import com.ssafy.arttab.like.Likes;
import com.ssafy.arttab.like.LikesRepository;
import com.ssafy.arttab.member.domain.Member;
import com.ssafy.arttab.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ArtworkService {

    private final ArtworkRepository artworkRepository;
    private final MemberRepository memberRepository;
    private final LikesRepository likeRepository;
    private final FollowRepository followRepository;

    @Transactional
    public List<ArtworkListResponseDto> getArtworkList(int page){

        Page<Artwork> result = artworkRepository.findAll(PageRequest.of(page, 20, Sort.by(Sort.Direction.DESC, "id")));
        return result.getContent().stream().map(ArtworkListResponseDto::new).collect(Collectors.toList());
    }

    @Transactional
    public boolean save(ArtworkFileDto artworkDto) {
    // artworkDto 정보를 artwork에 저장
        Artwork artwork = artworkRepository.save(
                Artwork.builder()
                        .writer(memberRepository.findById(artworkDto.getWriterId()).get())
                        .galleryItemList(null)
                        .title(artworkDto.getTitle())
                        .description(artworkDto.getDescription())
                        .originFileName(artworkDto.getOriginFileName())
                        .saveFileName(artworkDto.getSaveFileName())
                        .saveFolder(artworkDto.getSaveFolder())
                        .build()
        );

        if(artwork==null) return false; // 삽입 실패

        return true;
    }

    @Transactional
    public Optional<Artwork> update(Long id, ArtworkUpdateRequestDto requestDto){
        Optional<Artwork> artwork = artworkRepository.findById(id); // 수정할 작품 찾기

        // 저장된 정보 수정
        artwork.get().setTitle(requestDto.getTitle());
        artwork.get().setDescription(requestDto.getDescription());
        artwork.get().setOriginFileName(requestDto.getOriginFileName());
        artwork.get().setSaveFileName(requestDto.getSaveFileName());
        artwork.get().setSaveFolder(requestDto.getSaveFolder());

        return artwork;
    }

    // id에 해당하는 작품이 기존에 어디에 저장되어 있었는지 리턴한다
    public String getParentFile(Long id){

        Artwork artwork=artworkRepository.findById(id).get();
        String saveFolder=artwork.getSaveFolder();

        return saveFolder;
    }

    // id에 해당하는 작품의 상세 정보를 리턴한다
    public ArtworkResponseDto findByNo(Long id, Long loginMemberId){
        Artwork artwork=artworkRepository.findById(id).get(); // id에 해당하는 작품
        Member writer=artwork.getWriter(); // 작가
        boolean isLike=(likeRepository.selectIsLike(id, loginMemberId)>0)?true:false;
        boolean isFollow=(followRepository.isFollow(loginMemberId, writer.getId())>0)?true:false;

        ArtworkResponseDto response = ArtworkResponseDto.builder()
                .writerId(writer.getId())
                .writerNickname(writer.getNickname())
                .title(artwork.getTitle())
                .description(artwork.getDescription())
                .regdate(artwork.getRegdate())
                .artworkSaveFolder("file:///"+artwork.getSaveFolder())
                .writerProfileSaveFolder("file:///"+writer.getSaveFolder())
                .writerEmail(writer.getEmail())
                .likeNum(likeRepository.selectLikeNumByArtworkId(artwork.getId()))
                .likeOrNot(isLike)
                .followOrNot(isFollow)
                .build();

        return response;
    }

    // id를 아이디로 갖는 회원이 그린 작품들 리턴한다
    public List<ArtworkListResponseDto> getArtworkByMemberId(String nickname){
        Member member=memberRepository.findMemberByNickname(nickname); // 닉네임에 해당하는 회원 가져오기

        if(member == null) return null; // 회원 찾기에 실패했을 경우

        return member.getArtworkList().stream()
                .map(ArtworkListResponseDto::new)
                .sorted((a,b)->(int)(b.getArtworkId()-a.getArtworkId())) // 최근 순으로 정렬
                .collect(Collectors.toList());

    }

    @Transactional
    public void delete(Long id){
        Artwork artwork=artworkRepository.findById(id).get();
        artworkRepository.delete(artwork);
    }

    public List<LikeArtworkResponseDto> getLikeArtworkList(String nickname){

        Member member = memberRepository.findMemberByNickname(nickname);
        List<Likes> likes = likeRepository.selectByMemberId(member.getId());

        if(likes.isEmpty()) return null; // 좋아하는 작품이 없을 때에는 null 리턴

        List<LikeArtworkResponseDto> result=new ArrayList<>();

        for(Likes like: likes){
            Artwork artwork = like.getArtwork(); // 좋아요 한 작품
            Member writer = artwork.getWriter(); // 좋아요 한 작품의 작성자

            LikeArtworkResponseDto response = LikeArtworkResponseDto.builder()
                    .artworkTitle(artwork.getTitle())
                    .memberNickname(writer.getNickname())
                    .memberId(writer.getId())
                    .saveFolder("file:///"+artwork.getSaveFolder())
                    .likeOrNot(true)
                    .artworkId(artwork.getId())
                    .regdate(artwork.getRegdate())
                    .build();

            result.add(response);
        }

        Collections.sort(result);
        return result;
    }// end getLikeArtworkList

    // 팔로우한 회원의 작품 리스트
    public List<FollowArtworkListResponseDto> selectFollowArtworkList(String nickname) {

        Member member=memberRepository.findMemberByNickname(nickname); // nickname에 해당하는 사람
        List<Follow> followList=followRepository.findAllFollowing(member.getId()); // nickname이 팔로우하는 사람들 리스트
        List<FollowArtworkListResponseDto> result=new ArrayList<>(); // 팔로우한 회원의 작품 리스트를 위한 리스트

        if(followList==null) return null; // 팔로우하는 회원이 없으면 null 리턴

        for(Follow follow: followList){ // 팔로우하는 사람들 하나하나에 대해서
            Member followee = follow.getFollowee(); // 팔로우하는 사람
            List<Artwork> artworks=artworkRepository.find4ByMemberId(followee.getId()); // 4개 작품 가져오기
            List<SimpleArtworkDto> artworkInfo=new ArrayList<>();

            LocalDateTime recentUpdated=null;
            if(!artworks.isEmpty()){ // 작품을 작성했다면
                recentUpdated=artworks.get(0).getRegdate(); // 제일 최근에 작성한 날짜
                for(Artwork artwork: artworks){ // 작품들에 대해서
                    artworkInfo.add(new SimpleArtworkDto(artwork)); // 작품 아이디와 작품 폴더 저장
                }
            }

            FollowArtworkListResponseDto response = FollowArtworkListResponseDto.builder()
                    .artworkInfo(artworkInfo)
                    .artworkNum(artworkRepository.findNumByMemberId(followee.getId()))
                    .followerNum(followRepository.findAllFollowedCnt(followee.getId()))
                    .memberMail(followee.getEmail())
                    .recentUpdated(recentUpdated)
                    .build();

            result.add(response);
        }

        Collections.sort(result);
        return result;
    }

}
