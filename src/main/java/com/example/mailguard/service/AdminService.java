package com.example.mailguard.service;

import java.util.UUID;

public interface AdminService {

    void sendEmailByCategory(String categoryName);

    void changeCategoryStatus(boolean activate, UUID categoryId);
}
