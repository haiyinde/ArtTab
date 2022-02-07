package com.ssafy.arttab.exception.member;

import com.ssafy.arttab.exception.CustomException;
import org.springframework.http.HttpStatus;

/**
 * @packageName : com.ssafy.arttab.exception.member
 * @fileName : NoSuchMemberExcption
 * @date : 2022-02-06
 * @language : JAVA
 * @classification :
 * @time_limit : 2sec
 * @required_time : 00:40 ~ 01:22
 * @submissions : 1
 * @description :
 **/
public class NoSuchMemberExcption extends CustomException {
    private static final String MESSAGE = "fail";

    public NoSuchMemberExcption() {
        super(MESSAGE, HttpStatus.BAD_REQUEST);
    }
}
