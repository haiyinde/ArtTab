package com.ssafy.arttab.member.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @packageName : com.ssafy.arttab.member.dto
 * @fileName : LoginEmail
 * @date : 2022-02-06
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
public class LoginEmail {
    private String email;

    private LoginEmail(String email) {
        this.email = email;
    }
}
