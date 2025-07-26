package com.example.mailguard.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Update request")
public class EmailCategoryUpdateRequest {

    @Schema(description = "Category name", example = "Product Updates")
    @NotBlank(message = "${category.name.not-blank}")
    private String categoryName;

    @Schema(description = "Category description", example = "New features, improvements, and release notes")
    @NotBlank(message = "${category.description.not-blank}")
    private String description;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
