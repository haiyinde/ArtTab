package com.ssafy.arttab.member;

import com.ssafy.arttab.artwork.Artwork;
import com.ssafy.arttab.domain.gallery.GalleryItem;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Entity
@Getter
@Setter
public class Memberdto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    String nickname;

    @OneToMany(mappedBy="writer", cascade = CascadeType.REMOVE)
    private List<com.ssafy.arttab.domain.gallery.GalleryItem> galleryItemList; // 회원이 갖는 갤러리

    @OneToMany(mappedBy="writer", cascade = CascadeType.REMOVE)
    private List<Artwork> artworkList; // 회원이 만든 작품 리스트

    @Builder
    public Memberdto(String nickname, List<GalleryItem> galleryItemList, List<Artwork> artworkList) {
        this.nickname = nickname;
        this.galleryItemList = galleryItemList;
        this.artworkList = artworkList;
    }
}
