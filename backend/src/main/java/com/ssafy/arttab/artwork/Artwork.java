package com.ssafy.arttab.artwork;

import com.ssafy.arttab.domain.gallery.GalleryItem;
import com.ssafy.arttab.member.dto.Memberdto;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @packageName : com.ssafy.arttab.artwork
 * @fileName : Artwork
 * @date : 2022-02-03
 * @language : JAVA
 * @classification :
 * @time_limit : 2sec
 * @required_time : 00:40 ~ 01:22
 * @submissions : 1
 * @description :
 **/
@Getter
@Setter
@NoArgsConstructor
@Entity
@ToString
@EntityListeners(AuditingEntityListener.class) // 추가
public class Artwork {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 작품 식별번호

    @ManyToOne
    private Memberdto writer; // 작성자

    @OneToMany(mappedBy="artwork", cascade = CascadeType.REMOVE)
    private List<GalleryItem> galleryItemList; // 작품을 포함하는 갤러리아이템 null처리

    @Column(length = 30)
    private String title; // 작품 이름

    @Column(length = 100)
    private String desc; // 작품 설명

    @CreatedDate
    @Column
    private LocalDateTime regdate; // 작성일

    @Column(length=300)
    private String originFileName; // 원본 파일 이름

    @Column(length=300)
    private String saveFileName; // 서버에 저장된 파일 이름

    @Column(length = 300)
    private String saveFolder; // 저장된 폴더 경로

    @Builder
    public Artwork(Memberdto writer, List<GalleryItem> galleryItemList, String title, String desc, LocalDateTime regdate, String originFileName, String saveFileName, String saveFolder) {
        this.writer = writer;
        this.galleryItemList = galleryItemList; // 생성할 때 null로 만들기. 수정할 때는 닉네임으로 조회해와서 변경할 값을 set한 다음 save
        this.title = title;
        this.desc = desc;
        this.regdate = regdate;
        this.originFileName = originFileName;
        this.saveFileName = saveFileName;
        this.saveFolder = saveFolder;
    }

}
