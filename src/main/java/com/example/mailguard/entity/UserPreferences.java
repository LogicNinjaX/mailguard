package com.example.mailguard.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(
        name = "user_preferences",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_user_preferences_user_category",
                columnNames = {"user_id", "category_id"}
        )
)
public class UserPreferences {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID serial;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserProfile user;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private EmailCategories category;

    @Column(nullable = false)
    private boolean consent;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public UserPreferences() {
    }

    public UUID getSerial() {
        return serial;
    }

    public void setSerial(UUID serial) {
        this.serial = serial;
    }

    public UserProfile getUser() {
        return user;
    }

    public void setUser(UserProfile user) {
        this.user = user;
    }

    public EmailCategories getCategory() {
        return category;
    }

    public void setCategory(EmailCategories category) {
        this.category = category;
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
