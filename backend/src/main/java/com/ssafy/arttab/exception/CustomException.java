package com.ssafy.arttab.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * @packageName : com.ssafy.arttab.exception
 * @fileName : CustomExecption
 * @date : 2022-02-04
 * @language : JAVA
 * @classification :
 * @time_limit : 2sec
 * @required_time : 00:40 ~ 01:22
 * @submissions : 1
 * @description :
 **/
@Getter
public class CustomException extends RuntimeException{
    private final HttpStatus status;

    public CustomException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
