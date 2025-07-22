package com.example.mailguard.dto.response;


import java.time.LocalDateTime;
import java.util.UUID;

public class UserPreferenceWithUser {

    private UUID serial;

    private String username;

    private String categoryName;

    private boolean consent;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public UUID getSerial() {
        return serial;
    }

    public void setSerial(UUID serial) {
        this.serial = serial;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public boolean isConsent() {
        return consent;
    }

    public void setConsent(boolean consent) {
        this.consent = consent;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
