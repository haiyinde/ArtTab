package com.ssafy.arttab.member.domain;

import lombok.*;

import javax.persistence.*;

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


    public Member(String nickname,String password,String email,String intro){
        this.nickname = nickname;
        this.password = password;
        this.email = email;
        this.intro = intro;
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
