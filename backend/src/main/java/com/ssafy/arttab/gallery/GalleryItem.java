package com.ssafy.arttab.domain.gallery;

import com.ssafy.arttab.artwork.Artwork;
import com.ssafy.arttab.member.dto.Memberdto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class GalleryItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 갤러리 식별번호

    @ManyToOne
    @JoinColumn(name="writer")
    private Memberdto writer; // 작성자

    @ManyToOne
    @JoinColumn(name="artwork")
    private Artwork artwork; // 갤러리에 들어간 작품들

    @Column(name = "x_loc")
    private double xLoc; // x축 위치

    @Column(name="y_loc")
    private double yLoc; // y축 위치

    @Builder
    public GalleryItem(Memberdto writer, Artwork artwork, double xLoc, double yLoc){
        this.writer=writer;
        this.artwork=artwork;
        this.xLoc=xLoc;
        this.yLoc=yLoc;
    }

}
