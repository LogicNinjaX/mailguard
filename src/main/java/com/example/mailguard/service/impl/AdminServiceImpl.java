package com.example.mailguard.service.impl;

import com.example.mailguard.repository.UserPreferencesRepository;
import com.example.mailguard.service.AdminService;
import com.example.mailguard.service.EmailService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AdminServiceImpl implements AdminService {

    private final UserPreferencesRepository userPreferencesRepository;
    private final EmailService emailService;

    public AdminServiceImpl(UserPreferencesRepository userPreferencesRepository, EmailService emailService) {
        this.userPreferencesRepository = userPreferencesRepository;
        this.emailService = emailService;
    }

    public void sendEmailByCategory(String categoryName){
        List<String> userEmails = userPreferencesRepository.getAllEmails(categoryName);

        if (!userEmails.isEmpty()){
            emailService.sendBulkEmail(userEmails, "Test message", "Email sent from spring boot");
        }
    }

}
