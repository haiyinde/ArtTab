package com.ssafy.arttab.follow;

import com.ssafy.arttab.member.dto.Memberdto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "follower")
    private Memberdto follower;

    @ManyToOne
    @JoinColumn(name = "followee")
    private Memberdto followee;

    @Builder
    public Follow(Memberdto follower, Memberdto followee) {
        this.follower = follower;
        this.followee = followee;
    }
}
