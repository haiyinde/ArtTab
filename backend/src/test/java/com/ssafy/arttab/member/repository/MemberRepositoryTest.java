package com.ssafy.arttab.member.repository;

import com.ssafy.arttab.exception.member.NoSuchMemberException;
import com.ssafy.arttab.member.domain.Member;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @packageName : com.ssafy.arttab.member
 * @fileName : MemberRepositoryTest
 * @date : 2022-02-01
 * @language : JAVA
 * @classification :
 * @time_limit : 2sec
 * @required_time : 00:40 ~ 01:22
 * @submissions : 1
 * @description :
 **/

@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;


    static private Member member;
    @BeforeAll
    static void setUp(){
        member = Member.builder()
                .nickname("ddd")
                .password("123")
                .email("aa@daum.com")
                .build();
    }

    @Test
    @DisplayName("닉네임 중복")
    void checkNick(){
        memberRepository.save(member);
        Assertions.assertTrue(memberRepository.existsMembersByNickname(member.getNickname()));
    }

    @Test
    @DisplayName("이메일 중복")
    void checkemail(){
        memberRepository.save(member);
        Assertions.assertTrue(memberRepository.existsMembersByEmail(member.getEmail()));
    }

    @Test
    @DisplayName("성공:회원 등록")
    void savemember1(){
        var member2 = Member.builder()
                .nickname("1")
                .password("123")
                .email("1@naver.com")
                .build();

        memberRepository.save(member);
        memberRepository.save(member2);
        memberRepository.findAll().forEach(System.out::println);


    }
    @Test
    @DisplayName("실패:회원 등록-중복오류")
    void savemember2(){
        String message = "success";
        var member2 = Member.builder()
                .nickname("ddd")
                .password("123")
                .email("asf@dasfhjk")
                .build();

        memberRepository.save(member);
        try {
            memberRepository.save(member2);

        }catch (Exception e){
            message = e.getMessage();
        }
        Assertions.assertEquals(message,"fail");
    }

    @Test
    @DisplayName("회원 이메일로 식별번호 찾기 ")
    void findIdbyemail(){
        memberRepository.save(member);
        Member member1 = memberRepository.findMemberByEmail("aa@daum.com").orElseThrow(NoSuchMemberException::new);
        Assertions.assertEquals(member.getId(),1L);
    }
}