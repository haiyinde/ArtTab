package com.ssafy.arttab.gallery;

import com.ssafy.arttab.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GalleryItemRepository extends JpaRepository<GalleryItem, Long> {

    List<GalleryItem> findGalleryItemByWriter(Member member);

}
