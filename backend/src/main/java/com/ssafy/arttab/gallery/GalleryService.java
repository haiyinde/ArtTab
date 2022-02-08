package com.ssafy.arttab.gallery;

import com.ssafy.arttab.artwork.ArtworkRepository;
import com.ssafy.arttab.gallery.dto.GalleryItemSaveRequestDto;
import com.ssafy.arttab.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.ssafy.arttab.domain.gallery.GalleryItem;
import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service
public class GalleryService {

    private final MemberRepository memberRepository;
    private final ArtworkRepository artworkRepository;
    private final GalleryItemRepository galleryRepository;

    @Transactional
    public boolean insertGalleryItemList(String nickname, List<GalleryItemSaveRequestDto> list){

        return true;
    }
}
