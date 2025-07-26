package com.example.mailguard.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Register request entity")
public class EmailCategoryRegisterRequest {

    @Schema(description = "Email category name", example = "Marketing")
    @NotBlank(message = "${category.name.not-blank}")
    private String categoryName;

    @Schema(description = "Email category description", example = "Promotional offers, coupons, and discounts")
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
