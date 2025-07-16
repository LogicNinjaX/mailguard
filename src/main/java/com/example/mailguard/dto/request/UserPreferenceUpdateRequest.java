package com.example.mailguard.dto.request;

import java.util.UUID;

public class UserPreferenceUpdateRequest {

    private UUID preferenceId;
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
