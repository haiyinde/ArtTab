package com.ssafy.arttab.member.repository;

import com.ssafy.arttab.member.domain.MailAuth;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @packageName : com.ssafy.arttab.member.repository
 * @fileName : MailAuthRepogitoryTest
 * @date : 2022-02-05
 * @language : JAVA
 * @classification :
 * @time_limit : 2sec
 * @required_time : 00:40 ~ 01:22
 * @submissions : 1
 * @description :
 **/
@SpringBootTest
class MailAuthRepogitoryTest {

    @Autowired
    MailAuthRepogitory mailAuthRepogitory;

    MailAuth mailAuth;

    @BeforeEach
    void setUp() {
      mailAuth = MailAuth.builder()
              .id("1")
              .memNo(1L)
              .build();
    }

    @Test
    @DisplayName("메일 인증 등록")
    void saveMailAuth(){
        System.out.println(mailAuth);

        mailAuthRepogitory.save(mailAuth);
        mailAuthRepogitory.findAll().forEach(System.out::println);
    }
}