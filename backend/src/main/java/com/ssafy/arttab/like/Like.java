package com.ssafy.arttab.like;

import com.ssafy.arttab.BaseTimeEntity;
import com.ssafy.arttab.artwork.Artwork;
import com.ssafy.arttab.member.domain.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "likes")
public class Like extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "artwork")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Artwork artwork;

    @ManyToOne
    @JoinColumn(name = "member")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Member member;

    @Builder
    public Like(Artwork artwork, Member member) {
        this.artwork = artwork;
        this.member = member;
    }


}
