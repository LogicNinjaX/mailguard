package com.example.mailguard.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

@Schema(description = "Preference update request")
public class UserPreferenceUpdateRequest {

    @Schema(description = "User preference id", example = "0xE0EDB5DB530B489D89D7727113004C3A")
    @NotBlank(message = "${user.preference-id.not-blank}")
    private UUID preferenceId;

    @Schema(description = "User consent", examples = {"true", "false"})
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
