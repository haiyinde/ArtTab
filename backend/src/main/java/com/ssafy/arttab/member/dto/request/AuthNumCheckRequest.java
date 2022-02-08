package com.ssafy.arttab.member.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

/**
 * @packageName : com.ssafy.arttab.member.dto.request
 * @fileName : AuthNumCheckRequest
 * @date : 2022-02-06
 * @language : JAVA
 * @classification :
 * @time_limit : 2sec
 * @required_time : 00:40 ~ 01:22
 * @submissions : 1
 * @description :
 **/
@Getter
@NoArgsConstructor
public class AuthNumCheckRequest {
    @Column(length=50, nullable=false, unique = true)
    private String email;
    private String id;

    @Builder
    public AuthNumCheckRequest(String emaill,String id) {
        this.email = emaill;
        this.id = id;
    }
}
