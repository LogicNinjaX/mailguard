package com.example.mailguard.service.impl;

import com.example.mailguard.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;
    private static final Logger LOGGER = LoggerFactory.getLogger(EmailServiceImpl.class);

    public EmailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Value("${spring.mail.username}")
    private String senderEmail;


    @Async
    @Override
    public void sendEmail(String email, String subject, String body) {

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(body);
        simpleMailMessage.setFrom(senderEmail);

        try {
            javaMailSender.send(simpleMailMessage);
            LOGGER.info("Email sent to: {}", email);
        } catch (Exception e) {
            LOGGER.error("Failed to send email to: {} - more info: {}", email, e.getMessage());
        }
    }


    @Override
    public void sendBulkEmail(List<String> emails, String subject, String body){
        for (String email : emails){
            sendEmail(email, subject, body);
        }
    }

}
