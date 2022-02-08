package com.ssafy.arttab.member.dto;

/**
 * @packageName : com.ssafy.arttab.member.dto
 * @fileName : ValidatorMessage
 * @date : 2022-02-03
 * @language : JAVA
 * @classification :
 * @time_limit : 2sec
 * @required_time : 00:40 ~ 01:22
 * @submissions : 1
 * @description :
 **/
public class ValidatorMessage {
    private ValidatorMessage(){

    }

    public static final String EMPTY_MESSAGE = "항목을 입력해주세요.";
    public static final String EMAIL_MESSAGE = "올바른 이메일 형식이 아닙니다.";
    public static final String MEMBER_PW_MESSAG = "비밀번호를 다시 확인해 주세요";
    public static final String MEMBER_NICK_MESSAG = "닉네임을 다시 확인해 주세요";
}
