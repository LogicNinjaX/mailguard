package com.example.mailguard.controller;

import com.example.mailguard.dto.request.EmailCategoryRegisterRequest;
import com.example.mailguard.dto.request.EmailCategoryUpdateRequest;
import com.example.mailguard.dto.response.ApiResponse;
import com.example.mailguard.dto.response.EmailCategoryResponse;
import com.example.mailguard.service.EmailCategoriesService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@PreAuthorize("hasRole('ADMIN')")
public class EmailCategoryController {

    private final EmailCategoriesService categoryService;

    public EmailCategoryController(EmailCategoriesService categoryService) {
        this.categoryService = categoryService;
    }


    @PostMapping("/category")
    public ResponseEntity<ApiResponse<EmailCategoryResponse>> creteCategory(@Valid @RequestBody EmailCategoryRegisterRequest request)
    {
        EmailCategoryResponse response = categoryService.createCategory(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "category created successfully", response));
    }


    @GetMapping("/category/{category-id}")
    public ResponseEntity<ApiResponse<EmailCategoryResponse>> getCategory(@PathVariable("category-id")UUID categoryId){
        EmailCategoryResponse response = categoryService.getCategory(categoryId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(true, "category fetched successfully", response));
    }

    @GetMapping("/category")
    public ResponseEntity<ApiResponse<List<EmailCategoryResponse>>> getCategories(
            @RequestParam(defaultValue = "1", required = false) int page,
            @RequestParam(defaultValue = "10", required = false) int size,
            @RequestParam(defaultValue = "categoryName", required = false) String sortBy,
            @RequestParam(defaultValue = "desc", required = false) String dir
    )
    {
        List<EmailCategoryResponse> categoryList = categoryService.getAllCategories(page, size, dir, sortBy);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(true, "request successful", categoryList));
    }

    @PutMapping("/category/{category-id}")
    public ResponseEntity<ApiResponse<EmailCategoryResponse>> updateCategory(
            @PathVariable("category-id") UUID categoryId,
            @Valid @RequestBody EmailCategoryUpdateRequest request
    ){
        EmailCategoryResponse updatedCategory = categoryService.updateCategory(categoryId, request);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(true, "category updated successfully", updatedCategory));
    }

    @DeleteMapping("/category/{category-id}")
    public ResponseEntity<ApiResponse<Void>> deleteCategory(@PathVariable("category-id") UUID categoryId){
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(new ApiResponse<>(true, "category deleted successfully", null));
    }
}
