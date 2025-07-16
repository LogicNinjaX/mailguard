package com.example.mailguard.service;

import com.example.mailguard.dto.request.EmailCategoryRegisterRequest;
import com.example.mailguard.dto.request.EmailCategoryUpdateRequest;
import com.example.mailguard.dto.response.EmailCategoryResponse;
import com.example.mailguard.exception.CategoryNotFoundException;

import java.util.List;
import java.util.UUID;

public interface EmailCategoriesService {

    EmailCategoryResponse createCategory(EmailCategoryRegisterRequest request);

    EmailCategoryResponse getCategory(UUID categoryId) throws CategoryNotFoundException;

    EmailCategoryResponse updateCategory(UUID categoryId, EmailCategoryUpdateRequest request) throws CategoryNotFoundException;

    void deleteCategory(UUID categoryId);

    List<EmailCategoryResponse> getAllCategories(int pageNumber, int pageSize, String dir, String sortBy);
}
