package com.ssafy.arttab.member.controller;

import com.ssafy.arttab.member.service.AuthService;
import com.ssafy.arttab.member.service.MemberService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * @packageName : com.ssafy.arttab.member.controller
 * @fileName : AuthController
 * @date : 2022-02-14
 * @language : JAVA
 * @classification :
 * @time_limit : 2sec
 * @required_time : 00:40 ~ 01:22
 * @submissions : 1
 * @description :
 **/
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final MemberService memberService;
    private final AuthService authService;

    @ApiOperation(value = "카카오 회원 가입")
    @GetMapping("/kakaologin")
    public ResponseEntity kakaologin(@RequestParam String authorize) {
        String access_token = authService.getAccessToken(authorize);
        HashMap<String, String> userinfo = authService.getUserInfo(access_token);
        return ResponseEntity.ok().body(userinfo);
    }
}
