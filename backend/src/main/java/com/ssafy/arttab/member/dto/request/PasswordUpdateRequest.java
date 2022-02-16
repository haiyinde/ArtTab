package com.ssafy.arttab.member.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

/**
 * @packageName : com.ssafy.arttab.member.dto.request
 * @fileName : PasswordUpdateRequest
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
public class PasswordUpdateRequest {

    private String loginEmail;
    private String password;
    private String newPassword;

    public PasswordUpdateRequest(String loginEmail, String password, String newPassword) {
        this.loginEmail = loginEmail;
        this.password = password;
        this.newPassword = newPassword;
    }
}
