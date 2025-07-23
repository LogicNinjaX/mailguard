package com.example.mailguard.service;

import java.util.List;

public interface EmailService {
    void sendEmail(String email, String subject, String body);

    void sendBulkEmail(List<String> emails, String subject, String body);
}
