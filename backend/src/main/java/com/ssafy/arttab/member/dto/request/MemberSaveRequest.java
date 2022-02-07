package com.ssafy.arttab.member.dto.request;

import com.ssafy.arttab.member.domain.Member;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import static com.ssafy.arttab.member.dto.ValidatorMessage.*;



/**
 * @packageName : com.ssafy.arttab.member
 * @fileName : MemberDto
 * @date : 2022-02-03
 * @language : JAVA
 * @classification :
 * @time_limit : 2sec
 * @required_time : 00:40 ~ 01:22
 * @submissions : 1
 * @description :
 **/
@Getter
public class MemberSaveRequest {
    @NotBlank(message = MEMBER_NICK_MESSAG)
    private String nickname;
    @NotBlank(message = MEMBER_PW_MESSAG)
    private String password;

    @NotBlank(message = EMPTY_MESSAGE)
    @Email(message= EMAIL_MESSAGE)
    private String email;

    @Builder
    public MemberSaveRequest(String nickname,String password,String email){
        this.nickname = nickname;
        this.password = password;
        this.email = email;
    }

    public Member toEntity(){
        return Member.builder()
                .nickname(nickname)
                .password(password)
                .email(email)
                .build();
    }
}
