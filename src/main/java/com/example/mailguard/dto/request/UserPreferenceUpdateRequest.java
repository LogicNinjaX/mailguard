package com.example.mailguard.dto.request;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public class UserPreferenceUpdateRequest {

    @NotBlank(message = "${user.preference-id.not-blank}")
    private UUID preferenceId;

    @NotBlank(message = "${user.consent.not-blank}")
    private Boolean consent;

    public UUID getPreferenceId() {
        return preferenceId;
    }

    public void setPreferenceId(UUID preferenceId) {
        this.preferenceId = preferenceId;
    }

    public Boolean getConsent() {
        return consent;
    }

    public void setConsent(Boolean consent) {
        this.consent = consent;
    }
}
