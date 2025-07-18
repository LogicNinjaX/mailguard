package com.example.mailguard.repository;

import com.example.mailguard.entity.UserProfile;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;
import java.util.UUID;

public interface UserProfileRepository extends JpaRepository<UserProfile, UUID> {

    @Transactional
    @Modifying
    @Query("DELETE FROM UserProfile u WHERE u.userId = :userId")
    int deleteUser(UUID userId);

    Optional<UserProfile> findByUsername(String username) throws UsernameNotFoundException;
}
