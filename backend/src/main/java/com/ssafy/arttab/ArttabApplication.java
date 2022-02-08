package com.ssafy.arttab;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing // BaseTimeEntity(등록일)를 적용하기 위한 어노테이션입니다.
@SpringBootApplication
public class ArttabApplication {

    public static void main(String[] args) {
        SpringApplication.run(ArttabApplication.class, args);
    }

}
