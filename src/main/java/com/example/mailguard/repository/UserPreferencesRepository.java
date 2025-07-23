package com.example.mailguard.repository;

import com.example.mailguard.entity.UserPreferences;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface UserPreferencesRepository extends JpaRepository<UserPreferences, UUID> {

    @Query("SELECT COUNT(up) FROM UserPreferences up WHERE up.user.userId = :userId AND up.category.categoryId = :categoryId")
    int isPreferenceExists(UUID userId, UUID categoryId);

    @Query("SELECT up FROM UserPreferences up WHERE up.user.userId = :userId")
    Page<UserPreferences> findByUserId(UUID userId, Pageable pageable);

    @Query("SELECT up.user.email FROM UserPreferences up WHERE up.consent = true AND up.category.isActive = true AND up.category.categoryName = :categoryName")
    List<String> getAllEmails(String categoryName);
}
