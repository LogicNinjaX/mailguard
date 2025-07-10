package com.example.mailguard.repository;

import com.example.mailguard.entity.EmailCategories;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EmailCategoriesRepository extends JpaRepository<EmailCategories, UUID> {
}
