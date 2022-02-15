package com.ssafy.arttab.gallery;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ssafy.arttab.artwork.Artwork;
import com.ssafy.arttab.member.domain.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "galleryitem")
public class GalleryItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 갤러리 식별번호

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name="writer")
    private Member writer; // 작성자

    @ManyToOne
    @JsonIgnore
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name="artwork")
    private Artwork artwork; // 갤러리에 들어간 작품들

    @Column(name = "x_loc")
    private double xLoc; // x축 위치

    @Column(name="y_loc")
    private double yLoc; // y축 위치
    
    @Column
    private double weight; // 가로

    @Column
    private double height; // 세로

    @Builder
    public GalleryItem(Member writer, Artwork artwork, double xLoc, double yLoc, double weight, double height) {
        this.writer = writer;
        this.artwork = artwork;
        this.xLoc = xLoc;
        this.yLoc = yLoc;
        this.weight = weight;
        this.height = height;
    }
}
