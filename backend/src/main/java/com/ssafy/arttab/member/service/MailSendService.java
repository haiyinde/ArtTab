package com.ssafy.arttab.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

/**
 * @packageName : com.ssafy.arttab.member.mailsender
 * @fileName : MailSendService
 * @date : 2022-02-05
 * @language : JAVA
 * @classification :
 * @time_limit : 2sec
 * @required_time : 00:40 ~ 01:22
 * @submissions : 1
 * @description :
 **/
@Service
@RequiredArgsConstructor
public class MailSendService {

    private final JavaMailSender sender;

    public void sendEmail(String to,String subject,String body){

        MimeMessage msg = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg);
        try {
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body);
        }catch (Exception e) {
            e.printStackTrace();
        }

        sender.send(msg);

    }
}
