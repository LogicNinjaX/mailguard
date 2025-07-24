package com.example.mailguard.service.impl;

import com.example.mailguard.repository.EmailCategoriesRepository;
import com.example.mailguard.repository.UserPreferencesRepository;
import com.example.mailguard.service.AdminService;
import com.example.mailguard.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
public class AdminServiceImpl implements AdminService {

    private final UserPreferencesRepository userPreferencesRepository;
    private final EmailService emailService;
    private final EmailCategoriesRepository emailCategoriesRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(AdminServiceImpl.class);

    public AdminServiceImpl(UserPreferencesRepository userPreferencesRepository, EmailService emailService, EmailCategoriesRepository emailCategoriesRepository) {
        this.userPreferencesRepository = userPreferencesRepository;
        this.emailService = emailService;
        this.emailCategoriesRepository = emailCategoriesRepository;
    }

    public void sendEmailByCategory(String categoryName){
        List<String> userEmails = userPreferencesRepository.getAllEmails(categoryName);

        if (!userEmails.isEmpty()){
            emailService.sendBulkEmail(userEmails, "Test message", "Email sent from spring boot");
        }
    }

    @Override
    public void changeCategoryStatus(boolean activate, UUID categoryId) {
        int updatedRows = emailCategoriesRepository.updateActivationStatus(activate, categoryId);
        if (updatedRows > 0){
            LOGGER.info("Category status updated successfully for categoryId= {}", categoryId);
        }
    }


}
