package com.ssafy.arttab.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.internet.MimeMessage;
import java.util.HashMap;

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
    private final SpringTemplateEngine templateEngine;


    public void sendEmail(String to,String subject,String templateName, HashMap<String,String > values){

        MimeMessage msg = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg);
        try {
            helper.setTo(to);
            helper.setSubject(subject);
            Context context = new Context();

            values.forEach((key, value)->{ context.setVariable(key, value); });

            String html = templateEngine.process(templateName, context);
            helper.setText(html, true);
        }catch (Exception e) {
            e.printStackTrace();
        }

        sender.send(msg);

    }
}
