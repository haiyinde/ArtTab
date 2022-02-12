package com.ssafy.arttab.comment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ssafy.arttab.BaseTimeEntity;
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
@Table(name = "comment")
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(length = 100, nullable = false)
    private String content;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "artwork", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Artwork artwork;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "member")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Member member;


    @Builder
    public Comment(String content, Artwork artwork, Member member){
        this.content = content;
        this.artwork = artwork;
        this.member = member;
    }

    public void update(String content) {
        this.content = content;
    }

}
