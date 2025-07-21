package com.example.mailguard.dto.request;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public class UserPreferenceSaveRequest {

    @NotBlank(message = "${category.id.not-blank}")
    UUID categoryId;

    @NotBlank(message = "${user.consent.not-blank}")
    boolean consent;

    public UUID getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(UUID categoryId) {
        this.categoryId = categoryId;
    }

    public boolean getConsent() {
        return consent;
    }

    public void setConsent(boolean consent) {
        this.consent = consent;
    }
}
