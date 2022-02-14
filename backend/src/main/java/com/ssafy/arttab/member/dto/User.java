package com.ssafy.arttab.member.dto;

import com.ssafy.arttab.member.domain.Member;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import java.util.Collection;

/**
 * @packageName : com.ssafy.arttab.member.dto
 * @fileName : User
 * @date : 2022-02-07
 * @language : JAVA
 * @classification :
 * @time_limit : 2sec
 * @required_time : 00:40 ~ 01:22
 * @submissions : 1
 * @description :
 **/

@Data
public class User implements UserDetails {

    private String password;
    @Column(length=50, nullable=false, unique = true)
    private String email;

    private User(String email,String password) {
        this.email = email;
        this.password = password;
    }

    public Member toEntity() {

        Member build = Member.builder()
                .email(email)
                .password(password)
                .build();

        return build;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
