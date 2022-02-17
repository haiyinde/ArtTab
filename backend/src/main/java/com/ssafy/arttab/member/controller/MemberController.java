package com.ssafy.arttab.member.controller;


import com.ssafy.arttab.artwork.dto.MD5Generator;
import com.ssafy.arttab.exception.member.DuplicateException;
import com.ssafy.arttab.member.dto.LoginEmail;
import com.ssafy.arttab.member.dto.User;
import com.ssafy.arttab.member.dto.request.AuthNumCheckRequest;
import com.ssafy.arttab.member.dto.request.IntroUpdateRequest;
import com.ssafy.arttab.member.dto.request.MemberSaveRequest;
import com.ssafy.arttab.member.dto.request.PasswordUpdateRequest;
import com.ssafy.arttab.member.dto.response.MemberInfoResponse;
import com.ssafy.arttab.member.dto.response.ProfileInfoResponse;
import com.ssafy.arttab.member.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/member")
@Api("멤버 컨트롤러")
public class MemberController {

    @Value("${access.url.location}")
    private String location;

    private final MemberService memberService;

    @ApiOperation(value = "회원가입", notes = "회원가입 DB입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다", response = String.class)
    @PostMapping("/signup")
    public ResponseEntity<String> insertMember(@Valid @RequestBody MemberSaveRequest memberSaveRequest) {
        String message = "success";
        try{
            memberService.saveMember(memberSaveRequest);
        }catch (Exception e){
            message =e.getMessage();
        }

        return new ResponseEntity<String>(message, HttpStatus.OK);
    }

    @ApiOperation(value = "이메일 재전송", notes = "인증번호변경후 해당이메일로 전송")
    @PostMapping("/email")
    public ResponseEntity<String> sendEmail(@Valid @RequestBody LoginEmail loginEmail) {
        String message = "success";

            memberService.SendNumtoEmail(loginEmail.getEmail());


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
            message =e.getMessage();
        }
        return new ResponseEntity<>(message,HttpStatus.OK);
    }

    @ApiOperation(value = "로그인", notes = "Access-token과 로그인 결과 메시지를 반환한다.", response = String.class)
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @ApiParam(value = "로그인 시 필요한 회원정보 (이메일, 비밀번호).", required = true) User user) {
        return ResponseEntity.ok().body(memberService.login(user));
    }

    @ApiOperation(value = "닉네임 중복체크")
    @GetMapping("/idCk")
    public ResponseEntity<String> selectOnebynick(@RequestParam String nickname){
        String message = "success";
        memberService.MemberIdCheck(nickname);

        return new ResponseEntity<String>(message,HttpStatus.OK);
    }

    @ApiOperation(value = "이메일 중복체크")
    @GetMapping("/emailCk")
    public ResponseEntity<String> selectOnebyemail(@Email @RequestParam String email){
        System.out.println(email);
        String message = "success";
        memberService.MemberEmailCheck(email);

        return new ResponseEntity<String>(message,HttpStatus.OK);
    }

    @ApiOperation(value = "비밀번호 수정", notes = "비밀번호 DB수정 성공여부에 따라 'success 또는 'fail' 문자열을 반환한다.", response = String.class)
    @PutMapping("/me/password")
    public ResponseEntity<String> updatePassword(@RequestBody PasswordUpdateRequest passwordUpdateRequest) {
        memberService.updatePassword(passwordUpdateRequest);
        return new ResponseEntity<String>("success", HttpStatus.OK);
    }

    @ApiOperation(value = "회원 정보 조회", notes = "회원 정보를 담은 Token을 반환한다.")
    @GetMapping("/me")
    public ResponseEntity<MemberInfoResponse> selectMember(LoginEmail loginEmail) {
        var memberInfoResponse = memberService.getMemberInfo(loginEmail);
        return ResponseEntity.ok().body(memberInfoResponse);
    }

    @ApiOperation(value = "회원삭제", notes = "회원번호로 DB 삭제 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
    @DeleteMapping("/me/{id}")
    public ResponseEntity<Void> deleteMember(@RequestParam Long id) {
        memberService.deleteMember(id);
        return ResponseEntity.noContent().build();
    }


    @ApiOperation(value = "비밀번호 찾기")
    @PutMapping("/password")
    public ResponseEntity<String> findPassword(@RequestBody LoginEmail loginEmail){
        memberService.findPassword(loginEmail.getEmail());
        return ResponseEntity.ok().body("success");
    }

    // 프로필 변경하기
    @ApiOperation(value = "회원 정보 변경하기")
    @PutMapping("/profile")
    public ResponseEntity<String> insertProfile(@RequestPart("file") MultipartFile file,
                                                @RequestParam("email") String email,
                                                @RequestParam(value = "nickname", required = false) String nickname,
                                                @RequestParam(value = "intro", required = false) String intro) throws IOException, NoSuchAlgorithmException {

        // 닉네임 중복 체크
        String message="success";
        try{
            memberService.MemberIdCheck(nickname);
        }catch (DuplicateException e){
            message= e.getMessage();
        }


        // 원래 프로필 사진 삭제하기
        String parentSaveFolder=memberService.getParentFolder(email); // 이메일에 해당하는 사용자의 원래 프로필 사진
        String defaultSaveFolder = "";
        if ("dev".equals(location)){
            defaultSaveFolder = System.getProperty("user.dir") + File.separator + "profile" + File.separator + "default.jpg"; // 기존에 프로필 사진 설정하지 않았을 경우
        }else if ("ec2".equals(location)){
            defaultSaveFolder = System.getProperty("user.dir")  + "img" + File.separator + "default.jpg"; // 기존에 프로필 사진 설정하지 않았을 경우
        }

        if(!parentSaveFolder.equals(defaultSaveFolder)) { // 기본 이미지로 설정되어 있는 경우에는 삭제 안함
            File parentFile=new File(parentSaveFolder);
            parentFile.delete();
        }

        // 프로필 사진 저장하기
        LocalDateTime time = LocalDateTime.now();
        String originFileName = file.getOriginalFilename();
        String saveFileName = new MD5Generator(originFileName + time).toString()+file.getOriginalFilename();
        String upperSavePath = "";
        String savePath = "";
        if ("dev".equals(location)){
            upperSavePath=System.getProperty("user.home")+ File.separator+"profile"; // 프로필 폴더
            savePath = upperSavePath; // 프로필 사진 주인 이메일
        }else if ("ec2".equals(location)){
            upperSavePath = System.getProperty("user.dir") + "img";
            savePath = upperSavePath + File.separator + "profile";
        }

        // profile 폴더가 없으면 폴더 생성
            if(!upperSavePath.isEmpty()){
                try{
                    new File(upperSavePath).mkdir();
                } catch(Exception e){
                    e.getStackTrace();
                }
            }

        // profile 폴더 아래에 사용자 폴더 만들기
        if (!new File(savePath).exists()) {
            try {
                new File(savePath).mkdir();
            } catch (Exception e) {
                e.getStackTrace();
            }
        }

        String saveFolder = savePath + File.separator + saveFileName;
        file.transferTo(new File(saveFolder)); // 파일 저장

        LoginEmail loginEmail=new LoginEmail(email);
        memberService.addNickname(loginEmail, nickname); // 닉네임 변경
        memberService.updateMember(loginEmail, new IntroUpdateRequest(intro)); // 자기소개 변경
        memberService.updateSaveFolder(loginEmail, saveFolder);
        memberService.updateSaveFilename(loginEmail, saveFileName);

       return new ResponseEntity<>(message, HttpStatus.OK);

    }

    @ApiOperation(value="회원 프로필 정보 리턴하기")
    @GetMapping("/profile")
    public ResponseEntity<ProfileInfoResponse> selectProfileInfo(@RequestParam("loginId") Long loginId, @RequestParam("profileMemberId") Long profileMemberId){

        ProfileInfoResponse result = memberService.getProfileInfo(loginId, profileMemberId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
