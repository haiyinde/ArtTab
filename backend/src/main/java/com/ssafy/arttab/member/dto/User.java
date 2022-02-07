package com.ssafy.arttab.member.dto;

import lombok.Data;

import javax.persistence.Column;

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
public class User {

    private String password;
    @Column(length=50, nullable=false, unique = true)
    private String email;

    private User(String email,String password) {
        this.email = email;
        this.password = password;
    }
}
