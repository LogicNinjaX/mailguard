package com.example.mailguard.service.impl;

import com.example.mailguard.dto.request.EmailCategoryRegisterRequest;
import com.example.mailguard.dto.request.EmailCategoryUpdateRequest;
import com.example.mailguard.dto.response.EmailCategoryResponse;
import com.example.mailguard.entity.EmailCategories;
import com.example.mailguard.exception.CategoryNotFoundException;
import com.example.mailguard.mapper.EmailCategoryMapper;
import com.example.mailguard.repository.EmailCategoriesRepository;
import com.example.mailguard.service.EmailCategoriesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EmailCategoriesServiceImpl implements EmailCategoriesService {

    private final EmailCategoriesRepository emailCategoriesRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(EmailCategoriesServiceImpl.class);
    private final EmailCategoryMapper emailCategoryMapper;

    public EmailCategoriesServiceImpl(EmailCategoriesRepository emailCategoriesRepository, EmailCategoryMapper emailCategoryMapper) {
        this.emailCategoriesRepository = emailCategoriesRepository;
        this.emailCategoryMapper = emailCategoryMapper;
    }

    @Override
    public EmailCategoryResponse createCategory(EmailCategoryRegisterRequest request){
        EmailCategories category = emailCategoryMapper.registerRequestToEmailCategories(request);
        category = emailCategoriesRepository.save(category);
        LOGGER.info("Email category created successfully with name: {}", request.getCategoryName());
        return emailCategoryMapper.emailCategoriesToResponse(category);
    }

    @Override
    public EmailCategoryResponse getCategory(UUID categoryId){
        EmailCategories category = emailCategoriesRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException("category not found with id: "+categoryId));
        return emailCategoryMapper.emailCategoriesToResponse(category);
    }

    @Override
    public EmailCategoryResponse updateCategory(UUID categoryId, EmailCategoryUpdateRequest request){
        EmailCategories category = emailCategoriesRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException("category not found with id: "+categoryId));

        category.setCategoryName(request.getCategoryName());
        category.setDescription(request.getDescription());

        category = emailCategoriesRepository.save(category);
        LOGGER.info("category: {} updated successfully", request.getCategoryName());

        return emailCategoryMapper.emailCategoriesToResponse(category);
    }

    public void deleteCategory(UUID categoryId){
        int effectedRows = emailCategoriesRepository.deleteCategory(categoryId);
        if (effectedRows > 0){
            LOGGER.info("category deleted successfully");
        }
    }

    @Override
    public List<EmailCategoryResponse> getAllCategories(int pageNumber, int pageSize, String dir, String sortBy){

        Sort sort = Sort.by(Sort.Direction.ASC, sortBy);;

        if (dir.equalsIgnoreCase("desc")) {
            sort = Sort.by(Sort.Direction.DESC, sortBy);
        }

        Pageable pageable = PageRequest.of(pageNumber-1, pageSize, sort);

        return emailCategoriesRepository.findAll(pageable)
                .getContent()
                .stream()
                .map(emailCategoryMapper::emailCategoriesToResponse).toList();
    }
}
