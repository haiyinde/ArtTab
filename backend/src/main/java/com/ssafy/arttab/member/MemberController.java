package com.ssafy.arttab.member;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/member")
@Api("멤버 컨트롤러")
public class MemberController {

    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";

    @ApiOperation(value = "회원가입", notes = "회원가입 DB입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다", response = String.class)
    @PostMapping("/signup")
    public ResponseEntity<String> insertMember(@RequestBody Member member) {
        // if (memberService.insertMember(member)
        return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);

        //return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);

    }

    @ApiOperation(value = "로그인", notes = "Access-token과 로그인 결과 메시지를 반환한다.", response = String.class)
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody @ApiParam(value = "로그인 시 필요한 회원정보 (이메일, 비밀번호).", required = true) Member member) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;

        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    @ApiOperation(value = "비밀번호 수정", notes = "비밀번호 DB수정 성공여부에 따라 'success 또는 'fail' 문자열을 반환한다.", response = String.class)
    @PutMapping("/{no}")
    public ResponseEntity<String> updatePassword(@RequestBody Member member) {
        return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
    }

    @ApiOperation(value = "내 정보 조회", notes = "회원 정보를 담은 Token을 반환한다.", response = Map.class)
    @GetMapping("/{no}")
    public void selectMember() {
    }

    @ApiOperation(value = "회원삭제", notes = "회원번호로 DB 삭제 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
    @DeleteMapping("/{no}")
    public void deleteMember() {
    }

//    @ApiOperation(value = "내 정보 수정", notes = "회원번호로 DB 수정 성공 여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = Map.class)
//    @PutMapping("/{no}")
//    public void updateMember() {
//    }


}
