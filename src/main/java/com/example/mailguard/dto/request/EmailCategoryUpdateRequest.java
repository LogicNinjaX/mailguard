package com.example.mailguard.dto.request;


import jakarta.validation.constraints.NotBlank;

public class EmailCategoryUpdateRequest {

    @NotBlank(message = "${category.name.not-blank}")
    private String categoryName;

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
