package com.example.mailguard.service;

import java.util.List;

public interface EmailService {
    void sendBulkEmail(List<String> emails, String subject, String body);
}
