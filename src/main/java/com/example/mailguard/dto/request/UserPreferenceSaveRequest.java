package com.example.mailguard.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

@Schema(description = "Preference create request")
public class UserPreferenceSaveRequest {

    @Schema(description = "Email Category id", example = "0xE0EDB5DB530B489D89D7727113004C3A")
    @NotNull(message = "${category.id.not-null}")
    UUID categoryId;

    @Schema(description = "User consent", examples = {"true", "false"})
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
