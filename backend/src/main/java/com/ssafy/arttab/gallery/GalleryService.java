package com.ssafy.arttab.gallery;

import com.ssafy.arttab.artwork.ArtworkRepository;
import com.ssafy.arttab.gallery.dto.GalleryItemListResponseDto;
import com.ssafy.arttab.gallery.dto.GalleryItemSaveRequestDto;
import com.ssafy.arttab.member.domain.Member;
import com.ssafy.arttab.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class GalleryService {

    private final MemberRepository memberRepository;
    private final ArtworkRepository artworkRepository;
    private final GalleryItemRepository galleryRepository;

    @Transactional
    public boolean insertGalleryItemList(String nickname, List<GalleryItemSaveRequestDto> list){

        // 가져온 리스트를 galleryItem에 저장
        for(GalleryItemSaveRequestDto requestDto: list) {
            GalleryItem galleryItem = galleryRepository.save(GalleryItem.builder()
                            .writer(memberRepository.findMemberByNickname(nickname))
                            .artwork(artworkRepository.findById(requestDto.getArtworkId()).get())
                            .xLoc(requestDto.getXLoc())
                            .yLoc(requestDto.getYLoc())
                            .build()
            );

            if(galleryItem==null) return false;
        }

        return true;
    }

    // nickname에 해당하는 GalleryItem을 모두 삭제하는 메소드
    public void deleteGalleryItemAll(String nickname){

        Member writer=memberRepository.findMemberByNickname(nickname); // nickname에 해당하는 아이디
        List<GalleryItem> list=galleryRepository.findGalleryItemByWriter(writer);

        if(!list.isEmpty()) { // 회원이 갤러리 아이템을 가지고 있다면
            galleryRepository.deleteAll(list); // 검색한 회원의 갤러리 아이템 모두 삭제
        }
    }

    // nickname을 가진 회원의 갤러리의 작품 리스트를 가져오는 메소드
    public List<GalleryItemListResponseDto> selectGalleryItemList(String nickname){

        Member writer=memberRepository.findMemberByNickname(nickname); // nickname에 해당하는 멤버
        List<GalleryItem> list=galleryRepository.findGalleryItemByWriter(writer);
        List<GalleryItemListResponseDto> result = new ArrayList<>();

        for(GalleryItem item: list){
            result.add(GalleryItemListResponseDto.builder()
                    .artworkId(item.getArtwork().getId())
                    .saveFileName(item.getArtwork().getSaveFileName())
                    .saveFolder(item.getArtwork().getSaveFolder())
                    .xLoc(item.getXLoc())
                    .yLoc(item.getYLoc())
                    .build()
            );
        }

        return result;
    }
}
