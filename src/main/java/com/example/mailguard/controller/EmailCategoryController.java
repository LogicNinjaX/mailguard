package com.example.mailguard.controller;

import com.example.mailguard.dto.request.EmailCategoryRegisterRequest;
import com.example.mailguard.dto.request.EmailCategoryUpdateRequest;
import com.example.mailguard.dto.response.ApiResponse;
import com.example.mailguard.dto.response.EmailCategoryResponse;
import com.example.mailguard.service.EmailCategoriesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "Email Category Management", description = "Operations related to email category")
@SecurityRequirement(name = "bearerAuth")
public class EmailCategoryController {

    private final EmailCategoriesService categoryService;

    public EmailCategoryController(EmailCategoriesService categoryService) {
        this.categoryService = categoryService;
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/category", consumes = "application/json", produces = "application/json")
    @Operation(summary = "Create email category", description = "Returns saved details")
    public ResponseEntity<ApiResponse<EmailCategoryResponse>> creteCategory(@Valid @RequestBody EmailCategoryRegisterRequest request)
    {
        EmailCategoryResponse response = categoryService.createCategory(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "category created successfully", response));
    }


    @GetMapping(value = "/category/{category-id}", produces = "application/json")
    @Operation(summary = "Get category details by id", description = "Returns category details")
    public ResponseEntity<ApiResponse<EmailCategoryResponse>> getCategory(@PathVariable("category-id")UUID categoryId){
        EmailCategoryResponse response = categoryService.getCategory(categoryId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(true, "category fetched successfully", response));
    }

    @GetMapping(value = "/category", produces = "application/json")
    @Operation(summary = "Get all categories", description = "Returns list of categories in pages")
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

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(value = "/category/{category-id}", consumes = "application/json", produces = "application/json")
    @Operation(summary = "Update category by id", description = "Returns updated details")
    public ResponseEntity<ApiResponse<EmailCategoryResponse>> updateCategory(
            @PathVariable("category-id") UUID categoryId,
            @Valid @RequestBody EmailCategoryUpdateRequest request
    ){
        EmailCategoryResponse updatedCategory = categoryService.updateCategory(categoryId, request);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(true, "category updated successfully", updatedCategory));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/category/{category-id}")
    @Operation(summary = "Delete category by id")
    public ResponseEntity<ApiResponse<Void>> deleteCategory(@PathVariable("category-id") UUID categoryId){
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
