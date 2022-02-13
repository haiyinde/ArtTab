package com.ssafy.arttab.member.dto.response;

import lombok.Builder;
import lombok.Data;

/**
 * @packageName : com.ssafy.arttab.member.DTO
 * @fileName : MemberInfoResponse
 * @date : 2022-02-03
 * @language : JAVA
 * @classification :
 * @time_limit : 2sec
 * @required_time : 00:40 ~ 01:22
 * @submissions : 1
 * @description :
 **/
@Data
public class MemberInfoResponse {

    private Long id;
    private String nickname;
    private String email;
    private String intro;

    @Builder
    public MemberInfoResponse(Long id,String nickname,String email,String intro){
        this.id = id;
        this.nickname =nickname;
        this.email = email;
        this.intro = intro;
    }
}
