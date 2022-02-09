package com.ssafy.arttab.member;

import com.ssafy.arttab.exception.member.DuplicateException;
import com.ssafy.arttab.exception.member.NoSuchMemberException;
import com.ssafy.arttab.exception.member.PasswordMismatchException;
import com.ssafy.arttab.member.dto.LoginEmail;
import com.ssafy.arttab.member.dto.request.AuthNumCheckRequest;
import com.ssafy.arttab.member.dto.request.IntroUpdateRequest;
import com.ssafy.arttab.member.dto.request.MemberSaveRequest;
import com.ssafy.arttab.member.repository.MailAuthRepogitory;
import com.ssafy.arttab.member.repository.MemberRepository;
import com.ssafy.arttab.member.service.MemberService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

/**
 * @packageName : com.ssafy.arttab.member
 * @fileName : MemberServiceTest
 * @date : 2022-02-05
 * @language : JAVA
 * @classification :
 * @time_limit : 2sec
 * @required_time : 00:40 ~ 01:22
 * @submissions : 1
 * @description :
 **/
@SpringBootTest
class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MailAuthRepogitory mailAuthRepogitory;
    @Autowired
    MemberRepository memberRepository;

    private MemberSaveRequest member= MemberSaveRequest.builder()
            .password("123")
            .email("tph01198@naver.com")
            .build();;


    @AfterEach
    void done(){
        LoginEmail loginEmail = new LoginEmail();
        loginEmail.setEmail("tph01198@naver.com");
        memberService.deleteMember(loginEmail);
        System.out.println("삭제");
        memberRepository.findAll().forEach(System.out::println);
    }

    @Test
    @Transactional
    @DisplayName("회원 등록")
    void saveMember() {
        Assertions.assertTrue(memberService.saveMember(member));
        System.out.println(memberRepository.findById(1L));


    }

    @Test
    @Transactional
    @DisplayName("실패:회원 등록")
    void failsaveMember() {
        String message ="success";
        Assertions.assertTrue(memberService.saveMember(member));
        try{
            Assertions.assertTrue(memberService.saveMember(member));
        }catch (DuplicateException e){
            message = e.getMessage();
        }
        memberRepository.findAll().forEach(System.out::println);
        Assertions.assertEquals(message,"fail");




    }
    @Test
    @Transactional
    @DisplayName("닉네임 등록")
    void addnickname(){
        memberRepository.save(member.toEntity());
        memberRepository.findAll().forEach(System.out::println);
        LoginEmail loginEmail = new LoginEmail();
        loginEmail.setEmail("tph01198@naver.com");
        memberService.addNickname(loginEmail,"ajdkl");
        memberRepository.findAll().forEach(System.out::println);
    }
    @Test
    @Transactional
    @DisplayName("소개글 수정")
    void UpdateIntro() {
        memberRepository.save(member.toEntity());
        memberRepository.findAll().forEach(System.out::println);
        LoginEmail loginEmail = new LoginEmail();
        loginEmail.setEmail("tph01198@naver.com");
        IntroUpdateRequest memberUpdateRequest = new IntroUpdateRequest("kasdlfj");
        memberService.updateMember(loginEmail,memberUpdateRequest);
        memberRepository.findAll().forEach(System.out::println);
    }

    @Test
    @Transactional
    @DisplayName("회원정보 삭제")
    void memberEmailCheck() {
        memberRepository.save(member.toEntity());
        LoginEmail loginEmail = new LoginEmail();
        loginEmail.setEmail("tph01198@naver.com");
        memberService.deleteMember(loginEmail);
        memberRepository.findAll().forEach(System.out::println);
    }
    @Test
    @Transactional
    @DisplayName("실패:인증번호 확인")
    void failselectAuthNum(){
        memberService.saveMember(member);
        memberRepository.findAll().forEach(System.out::println);
        LoginEmail loginEmail = new LoginEmail();
        var authNumCheckRequest = new AuthNumCheckRequest("tph01198@naver.com","aaaa");
        Assertions.assertThrows(PasswordMismatchException.class, () -> memberService.selectMailAuthId(authNumCheckRequest));

    }
    @Test
    @Transactional
    @DisplayName("성공:인증번호 확인")
    void selectAuthNum(){
        memberService.saveMember(member);
        var mem = memberRepository.findByEmail(member.getEmail())
                .orElseThrow(NoSuchMemberException::new);
        var auth  = mailAuthRepogitory.findById(mem.getId())
                .orElseThrow(NoSuchMemberException::new);
        LoginEmail loginEmail = new LoginEmail();
        var authNumCheckRequest = new AuthNumCheckRequest("tph01198@naver.com",auth.getId());
        memberService.selectMailAuthId(authNumCheckRequest);
        mailAuthRepogitory.findAll().forEach(System.out::println);
        memberRepository.findAll().forEach(System.out::println);

    }
    @Test
    @Transactional
    @DisplayName("이메일로 인증번호 전송")
    void sendNumtoEmail() {
        memberRepository.save(member.toEntity());
        memberRepository.findAll().forEach(System.out::println);
        memberService.SendNumtoEmail("tph01198@naver.com");
        mailAuthRepogitory.findAll().forEach(System.out::println);
    }

    @Test
    @DisplayName("회원 정보 조회")
    void getInfo(){
        memberRepository.save(member.toEntity());
        LoginEmail loginEmail = new LoginEmail();
        loginEmail.setEmail("tph01198@naver.com");
        var result = memberService.getMemberInfo(loginEmail);
        System.out.println(result);
    }
}