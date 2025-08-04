package com.example.mailguard.service.impl;


import com.example.mailguard.dto.request.EmailCategoryRegisterRequest;
import com.example.mailguard.dto.request.EmailCategoryUpdateRequest;
import com.example.mailguard.dto.response.EmailCategoryResponse;
import com.example.mailguard.entity.EmailCategories;
import com.example.mailguard.exception.CategoryNotFoundException;
import com.example.mailguard.mapper.EmailCategoryMapper;
import com.example.mailguard.repository.EmailCategoriesRepository;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;


@ExtendWith(MockitoExtension.class)
public class EmailCategoriesServiceImplTest {

    @Mock
    private EmailCategoriesRepository emailCategoriesRepository;
    @Mock
    private EmailCategoryMapper emailCategoryMapper;

    @InjectMocks
    private EmailCategoriesServiceImpl categoryService;

    @Test
    @DisplayName("createCategory() should save category and return response")
    void createCategory_ShouldSaveCategory(){
        EmailCategoryRegisterRequest request = new EmailCategoryRegisterRequest();
        request.setCategoryName("Marketing");

        EmailCategories categoryEntity = new EmailCategories();
        categoryEntity.setCategoryName(request.getCategoryName());

        EmailCategoryResponse response = new EmailCategoryResponse();
        response.setCategoryName(request.getCategoryName());


        when(emailCategoryMapper.registerRequestToEmailCategories(request)).thenReturn(categoryEntity);
        when(emailCategoriesRepository.save(categoryEntity)).thenReturn(categoryEntity);
        when(emailCategoryMapper.emailCategoriesToResponse(categoryEntity)).thenReturn(response);

        EmailCategoryResponse result = categoryService.createCategory(request);

        assertEquals(request.getCategoryName(), result.getCategoryName());
        verify(emailCategoriesRepository).save(categoryEntity);
    }

    @Test
    @DisplayName("getCategory() should return response")
    void getCategory_ShouldReturnResponse(){
        UUID categoryId = UUID.randomUUID();

        EmailCategories requiredEntity = new EmailCategories();
        requiredEntity.setCategoryId(categoryId);
        requiredEntity.setCategoryName("Marketing");

        EmailCategoryResponse response = new EmailCategoryResponse();
        response.setCategoryId(categoryId);
        response.setCategoryName(requiredEntity.getCategoryName());

        when(emailCategoriesRepository.findById(categoryId)).thenReturn(Optional.of(requiredEntity));
        when(emailCategoryMapper.emailCategoriesToResponse(requiredEntity)).thenReturn(response);

        EmailCategoryResponse result = categoryService.getCategory(categoryId);

        assertEquals(categoryId, requiredEntity.getCategoryId());
        assertEquals(categoryId, result.getCategoryId());

        assertEquals("Marketing", requiredEntity.getCategoryName());
        assertEquals("Marketing", result.getCategoryName());
    }

    @Test
    @DisplayName("getCategory() should throw exception when category not found")
    void getCategory_ShouldThrowException(){
        UUID categoryId = UUID.randomUUID();

        when(emailCategoriesRepository.findById(categoryId)).thenReturn(Optional.empty());

        assertThrows(CategoryNotFoundException.class, () -> categoryService.getCategory(categoryId));
    }

    @Test
    @DisplayName("updateCategory() should update category and return response")
    void updateCategory_ShouldUpdateAndReturnResponse(){
        UUID categoryId = UUID.randomUUID();

        EmailCategories category = new EmailCategories();
        category.setCategoryId(categoryId);
        category.setCategoryName("Newsletters");
        category.setDescription("Weekly content, blog updates, and insights");

        EmailCategoryUpdateRequest request = new EmailCategoryUpdateRequest();
        request.setCategoryName("Marketing");
        request.setDescription("xyz");


        EmailCategoryResponse response = new EmailCategoryResponse();
        response.setCategoryName(request.getCategoryName());
        response.setDescription(request.getDescription());

        when(emailCategoriesRepository.findById(categoryId)).thenReturn(Optional.of(category));
        when(emailCategoriesRepository.save(category)).thenReturn(category);
        when(emailCategoryMapper.emailCategoriesToResponse(category)).thenReturn(response);

        EmailCategoryResponse result = categoryService.updateCategory(categoryId, request);

        assertEquals(request.getCategoryName(), result.getCategoryName());
        assertEquals(request.getDescription(), result.getDescription());

        verify(emailCategoriesRepository).save(category);
    }

    @Test
    @DisplayName("updateCategory() should throw exception when category not found")
    void updateCategory_ShouldThrowException(){
        UUID categoryId = UUID.randomUUID();
        EmailCategoryUpdateRequest request = new EmailCategoryUpdateRequest();
        request.setCategoryName("Marketing");

        when(emailCategoriesRepository.findById(categoryId)).thenReturn(Optional.empty());

        assertThrows(CategoryNotFoundException.class, () -> categoryService.updateCategory(categoryId, request));
    }

    @Test
    @DisplayName("deleteCategory() should delete category and log successful message")
    void deleteCategory_ShouldDeleteCategory(){
        UUID categoryId = UUID.randomUUID();

        when(emailCategoriesRepository.deleteCategory(categoryId)).thenReturn(1);
        categoryService.deleteCategory(categoryId);

        verify(emailCategoriesRepository).deleteCategory(categoryId);
    }
}
