package com.ssafy.arttab.config;

import com.ssafy.arttab.exception.authorization.InvalidTokenException;
import com.ssafy.arttab.exception.authorization.NoauthorizedMemberException;
import com.ssafy.arttab.exception.authorization.TokenExpiredException;
import com.ssafy.arttab.exception.member.DuplicateException;
import com.ssafy.arttab.exception.member.NoSuchMemberException;
import com.ssafy.arttab.exception.member.PasswordMismatchException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @packageName : com.ssafy.arttab.config
 * @fileName : ControllerAdvice
 * @date : 2022-02-08
 * @language : JAVA
 * @classification :
 * @time_limit : 2sec
 * @required_time : 00:40 ~ 01:22
 * @submissions : 1
 * @description :
 **/
@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(NoSuchMemberException.class)
    public ResponseEntity<String> noSuchMemberException(NoSuchMemberException e){

        return new ResponseEntity<String>(e.getMessage(),e.getStatus());
    }

    @ExceptionHandler(DuplicateException.class)
    public ResponseEntity<String> duplicateException(DuplicateException e){

        return new ResponseEntity<String>(e.getMessage(),e.getStatus());
    }

    @ExceptionHandler(PasswordMismatchException.class)
    public ResponseEntity<String> passwordMismatchException(PasswordMismatchException e){

        return new ResponseEntity<String>(e.getMessage(),e.getStatus());
    }
    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<?> invalidTokenException(InvalidTokenException e){

        return  new ResponseEntity<>(e.getMessage(),e.getStatus());
    }

    @ExceptionHandler(NoauthorizedMemberException.class)
    public ResponseEntity<?> noauthorizedMemberException(NoSuchMemberException e){

        return  new ResponseEntity<>(e.getMessage(),e.getStatus());
    }

    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<?> tokenExpiredException(TokenExpiredException e){

        return  new ResponseEntity<>(e.getMessage(),e.getStatus());
    }
}
