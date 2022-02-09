package com.ssafy.arttab.member;


import com.ssafy.arttab.exception.member.DuplicateException;
import com.ssafy.arttab.member.dto.LoginEmail;
import com.ssafy.arttab.member.dto.request.AuthNumCheckRequest;
import com.ssafy.arttab.member.dto.request.IntroUpdateRequest;
import com.ssafy.arttab.member.dto.request.MemberSaveRequest;
import com.ssafy.arttab.member.dto.request.PasswordUpdateRequest;
import com.ssafy.arttab.member.dto.response.MemberInfoResponse;
import com.ssafy.arttab.member.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/member")
@Api("멤버 컨트롤러")
public class MemberController {

    private final MemberService memberService;

    @ApiOperation(value = "회원가입", notes = "회원가입 DB입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다", response = String.class)
    @PostMapping("/signup")
    public ResponseEntity<String> insertMember(@Valid @RequestBody MemberSaveRequest memberSaveRequest) {
        String message = "success";
        try{
            memberService.saveMember(memberSaveRequest);
        }catch (Exception e){
            message ="fail";
        }

        return new ResponseEntity<String>(message, HttpStatus.OK);
    }

    @ApiOperation(value = "이메일 재전송", notes = "인증번호변경후 해당이메일로 전송")
    @PostMapping("/email")
    public ResponseEntity<String> sendEmail(@Valid @RequestBody LoginEmail loginEmail) {
        String message = "success";
        try{
            memberService.SendNumtoEmail(loginEmail.getEmail());

        }catch (Exception e){
            message ="fail";
        }
        return new ResponseEntity<>(message,HttpStatus.OK);
    }

    @ApiOperation(value = "인증번호 확인",notes = "인증번호를 확인한다")
    @PostMapping("/authNum")
    public ResponseEntity<String> selectMailAuthId(@RequestBody AuthNumCheckRequest authNumCheckRequest){
        String message = "success";
        try{
            memberService.selectMailAuthId(authNumCheckRequest);
        }catch (Exception e){
            message = e.getMessage();
        }

        return ResponseEntity.ok().body(message);
    }
    @ApiOperation(value = "닉네임 등록", notes = "닉네임을 등록한다.")
    @PostMapping("/nickname")
    public ResponseEntity<String> addNickname(@Valid @RequestBody LoginEmail loginEmail,String nickname) {
        String message = "success";
        try{
            memberService.addNickname(loginEmail,nickname);

        }catch (Exception e){
            message ="fail";
        }
        return new ResponseEntity<>(message,HttpStatus.OK);
    }

//    @ApiOperation(value = "로그인", notes = "Access-token과 로그인 결과 메시지를 반환한다.", response = String.class)
//    @PostMapping("/login")
//    public ResponseEntity<Map<String, Object>> login(@RequestBody @ApiParam(value = "로그인 시 필요한 회원정보 (이메일, 비밀번호).", required = true) Member member) {
//        Map<String, Object> resultMap = new HashMap<>();
//        HttpStatus status = null;
//
//        return new ResponseEntity<Map<String, Object>>(resultMap, status);
//    }

    @ApiOperation(value = "닉네임 중복체크")
    @PostMapping("/idCk")
    public ResponseEntity<String> selectOnebynick(@RequestBody String nickname){
        String message = "success";
        try{
            memberService.MemberIdCheck(nickname);
        }catch (DuplicateException e){
            message= e.getMessage();
        }

        return new ResponseEntity<String>(message,HttpStatus.OK);
    }

    @ApiOperation(value = "이메일 중복체크")
    @PostMapping("/emailCk")
    public ResponseEntity<String> selectOnebyemail(@Email @RequestBody String email){
        String message = "success";
        try{
            memberService.MemberIdCheck(email);
        }catch (DuplicateException e){
            message = e.getMessage();
        }

        return new ResponseEntity<String>(message,HttpStatus.OK);
    }
    @ApiOperation(value = "비밀번호 수정", notes = "비밀번호 DB수정 성공여부에 따라 'success 또는 'fail' 문자열을 반환한다.", response = String.class)
    @PutMapping("/me/password")
    public ResponseEntity<String> updatePassword(@RequestBody LoginEmail loginEmail, PasswordUpdateRequest passwordUpdateRequest) {
        memberService.updatePassword(loginEmail,passwordUpdateRequest);
        return new ResponseEntity<String>("success", HttpStatus.OK);
    }

    @ApiOperation(value = "회원 정보 조회", notes = "회원 정보를 담은 Token을 반환한다.")
    @GetMapping("/me")
    public ResponseEntity<MemberInfoResponse> selectMember(LoginEmail loginEmail) {
        var memberInfoResponse = memberService.getMemberInfo(loginEmail);
        return ResponseEntity.ok().body(memberInfoResponse);
    }


    @ApiOperation(value = "회원삭제", notes = "회원번호로 DB 삭제 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
    @DeleteMapping("/me")
    public ResponseEntity<Void> deleteMember(LoginEmail loginEmail) {
        memberService.deleteMember(loginEmail);
        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "회원 정보 수정(소개글 수정)", notes = "회원번호로 DB 수정 성공 여부에 따라 'success' 또는 'fail' 문자열을 반환한다.")
    @PutMapping("/me")
    public ResponseEntity<MemberInfoResponse> updateMember(LoginEmail loginEmail, IntroUpdateRequest introUpdateRequest) {
        memberService.updateMember(loginEmail,introUpdateRequest);
        return ResponseEntity.ok().build();
    }
    @ApiOperation(value = "비밀번호 찾기")
    @PutMapping("/password")
    public ResponseEntity<String> findPassword(String email){
        memberService.findPassword(email);
        return ResponseEntity.ok().body("success");
    }

}
