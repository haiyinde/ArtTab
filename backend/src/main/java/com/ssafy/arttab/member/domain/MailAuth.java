package com.ssafy.arttab.member.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @packageName : com.ssafy.arttab.member.mailsender
 * @fileName : MailAuth
 * @date : 2022-02-05
 * @language : JAVA
 * @classification :
 * @time_limit : 2sec
 * @required_time : 00:40 ~ 01:22
 * @submissions : 1
 * @description :
 **/
@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name = "Mail_Auth")
public class MailAuth {

    private String id;
    @Id
    @Column(name= "mem_no")
    @JoinColumn(name = "member_id")
    private Long memNo;

    private LocalDateTime regdate;

    @PrePersist
    public void createdAt() {
//        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        this.regdate = LocalDateTime.now();
    }
    @Builder
    public MailAuth(String id,Long memNo){
        this.id = id;
        this.memNo = memNo;
    }
}
