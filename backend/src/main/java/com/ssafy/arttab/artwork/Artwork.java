package com.ssafy.arttab.artwork;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ssafy.arttab.comment.Comment;
import com.ssafy.arttab.gallery.GalleryItem;
import com.ssafy.arttab.like.Likes;
import com.ssafy.arttab.member.domain.Member;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
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
    @JoinColumn(name = "writer")
    @JsonIgnore
    private Member writer; // 작성자

    @OneToMany(mappedBy="artwork", cascade = CascadeType.REMOVE)
    private List<GalleryItem> galleryItemList; // 작품을 포함하는 갤러리아이템 null처리

    @OneToMany(mappedBy = "artwork", cascade = CascadeType.REMOVE)
    private List<Likes> likeList;

    @OneToMany(mappedBy = "artwork", cascade = CascadeType.REMOVE)
    private List<Comment> commentList;

    @Column(length = 30, nullable = false)
    private String title; // 작품 이름

    @Column(length = 100)
    private String description; // 작품 설명

    @CreatedDate
    @Column
    private LocalDateTime regdate; // 작성일

    @Column(length=300, nullable = false)
    private String originFileName; // 원본 파일 이름

    @Column(length=300, nullable = false)
    private String saveFileName; // 서버에 저장된 파일 이름

    @Column(length = 300, nullable = false)
    private String saveFolder; // 저장된 폴더 경로

    @Builder
    public Artwork(Member writer, List<GalleryItem> galleryItemList, List<Comment> commentList, String title, String description, LocalDateTime regdate, String originFileName, String saveFileName, String saveFolder, List<Likes> likeList) {
        this.writer = writer;
        this.galleryItemList = galleryItemList; // 생성할 때 null로 만들기. 수정할 때는 닉네임으로 조회해와서 변경할 값을 set한 다음 save
        this.commentList = commentList;     // 생성할때 null, 작품 댓글 추가할 때마다 갱신
        this.title = title;
        this.description = description;
        this.regdate = regdate;
        this.originFileName = originFileName;
        this.saveFileName = saveFileName;
        this.saveFolder = saveFolder;
        this.likeList = likeList;   // 생성할때 0으로 초기화
    }

}
