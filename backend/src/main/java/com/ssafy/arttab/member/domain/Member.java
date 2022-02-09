package com.ssafy.arttab.member.domain;
import com.ssafy.arttab.comment.Comment;
import com.ssafy.arttab.gallery.GalleryItem;
import com.ssafy.arttab.artwork.Artwork;
import com.ssafy.arttab.like.Like;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Table(name = "member")
public class Member {
    @Id
    @GeneratedValue
    private Long id;
    @Column(length=30,unique = true)
    private String nickname;
    @Column(length=100, nullable=false)
    private String password;
    @Column(length=50, nullable=false, unique = true)
    private String email;
    @Column(length=100)
    private String intro;
    @Column(columnDefinition = "integer default 0")
    private int auth;

    @OneToMany(mappedBy="writer", cascade = CascadeType.REMOVE)
    private List<GalleryItem> galleryItemList; // 회원이 갖는 갤러리

    @OneToMany(mappedBy="writer", cascade = CascadeType.REMOVE)
    private List<Artwork> artworkList; // 회원이 만든 작품 리스트

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    private List<Comment> commentList;

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    private List<Like> likeList;

    @Builder
    public Member(String nickname, String password, String email, String intro, int auth, List<GalleryItem> galleryItemList, List<Artwork> artworkList) {
        this.nickname = nickname;
        this.password = password;
        this.email = email;
        this.intro = intro;
        this.auth = auth;
        this.galleryItemList = galleryItemList;
        this.artworkList = artworkList;
    }

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

    public void updateIntro(String intro) {
        this.intro = intro;
    }

    public void updatepassword(String password) {
        this.password = password;
    }

    public void updateAuth() {
        this.auth = 1;
    }
}
