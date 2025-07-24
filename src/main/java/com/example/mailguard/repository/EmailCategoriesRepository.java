package com.example.mailguard.repository;

import com.example.mailguard.entity.EmailCategories;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface EmailCategoriesRepository extends JpaRepository<EmailCategories, UUID> {

    @Modifying
    @Transactional
    @Query("DELETE FROM EmailCategories ec WHERE ec.categoryId = :categoryId")
    int deleteCategory(UUID categoryId);

    @Modifying
    @Transactional
    @Query("UPDATE EmailCategories ec SET ec.isActive = :activate WHERE ec.categoryId = :categoryId")
    int updateActivationStatus(boolean activate, UUID categoryId);
}
