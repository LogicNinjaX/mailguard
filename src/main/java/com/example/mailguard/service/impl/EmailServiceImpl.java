package com.example.mailguard.service.impl;

import com.example.mailguard.service.EmailService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailServiceImpl implements EmailService {

    private final AsyncEmailSender asyncEmailSender;

    public EmailServiceImpl(AsyncEmailSender asyncEmailSender) {
        this.asyncEmailSender = asyncEmailSender;
    }

    @Override
    public void sendBulkEmail(List<String> emails, String subject, String body){
        for (String email : emails){
            asyncEmailSender.sendEmail(email, subject, body);
        }
    }
}
