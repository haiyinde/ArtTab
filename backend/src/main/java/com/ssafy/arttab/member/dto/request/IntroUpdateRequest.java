package com.ssafy.arttab.member.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

/**
 * @packageName : com.ssafy.arttab.member.dto.request
 * @fileName : MemberUpdateRequest
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
public class IntroUpdateRequest {
    @Column(length=100)
    private String intro;


    public IntroUpdateRequest(String intro) {
        this.intro = intro;
    }
}
