package com.ssafy.arttab.artwork;

import com.ssafy.arttab.artwork.dto.ArtworkFileDto;
import com.ssafy.arttab.artwork.dto.ArtworkListResponseDto;
import com.ssafy.arttab.artwork.dto.ArtworkUpdateRequestDto;
import com.ssafy.arttab.member.domain.Member;
import com.ssafy.arttab.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ArtworkService {

    private final ArtworkRepository artworkRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public List<ArtworkListResponseDto> getArtworkList(){
        return artworkRepository.findAll(Sort.by(Sort.Direction.DESC, "id"))
                .stream().map(ArtworkListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public boolean save(ArtworkFileDto artworkDto) {
    // artworkDto 정보를 artwork에 저장
        Artwork artwork = artworkRepository.save(
                Artwork.builder()
                        .writer(memberRepository.findById(artworkDto.getWriterId()).get())
                        .galleryItemList(null)
                        .title(artworkDto.getTitle())
                        .desc(artworkDto.getDesc())
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
        artwork.get().setDesc(requestDto.getDesc());
        artwork.get().setOriginFileName(requestDto.getOriginFileName());
        artwork.get().setSaveFileName(requestDto.getSaveFileName());
        artwork.get().setSaveFolder(requestDto.getSaveFolder());

        return artwork;
    }

    public String getParentFile(Long id){

        Artwork artwork=artworkRepository.findById(id).get();
        String saveFolder=artwork.getSaveFolder();

        return saveFolder;
    }

    public Optional<Artwork> findByNo(Long id){
        Optional<Artwork> entity=artworkRepository.findById(id);

        return entity;
    }

    // id를 아이디로 갖는 회원이 그린 작품들 리턴
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

}
