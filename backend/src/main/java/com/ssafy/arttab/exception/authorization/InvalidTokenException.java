package com.ssafy.arttab.exception.authorization;

import com.ssafy.arttab.exception.CustomException;
import org.springframework.http.HttpStatus;

/**
 * @packageName : com.ssafy.arttab.exception.authorization
 * @fileName : InvalidTokenException
 * @date : 2022-02-14
 * @language : JAVA
 * @classification :
 * @time_limit : 2sec
 * @required_time : 00:40 ~ 01:22
 * @submissions : 1
 * @description :
 **/
public class InvalidTokenException extends CustomException {
    private static final String MESSAGE = "로그인이 필요한 서비스";

    public InvalidTokenException() {
        super(MESSAGE, HttpStatus.UNAUTHORIZED);
    }
}
