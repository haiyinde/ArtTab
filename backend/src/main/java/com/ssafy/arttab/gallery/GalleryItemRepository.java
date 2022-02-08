package com.ssafy.arttab.gallery;

import com.ssafy.arttab.domain.gallery.GalleryItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GalleryItemRepository extends JpaRepository<GalleryItem, Long> {
}
