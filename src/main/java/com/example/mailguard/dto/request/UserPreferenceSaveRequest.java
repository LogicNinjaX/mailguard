package com.example.mailguard.dto.request;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class UserPreferenceSaveRequest {

    @NotNull(message = "${category.id.not-null}")
    UUID categoryId;

    @NotNull(message = "${user.consent.not-null}")
    Boolean consent;

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
