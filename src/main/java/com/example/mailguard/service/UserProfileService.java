package com.example.mailguard.service;

import com.example.mailguard.dto.request.UserRegisterRequest;
import com.example.mailguard.dto.request.UserUpdateRequest;
import com.example.mailguard.dto.response.UserDetailsResponse;
import com.example.mailguard.dto.response.UserRegisterResponse;
import com.example.mailguard.dto.response.UserUpdateResponse;
import com.example.mailguard.enums.UserRole;
import com.example.mailguard.exception.UserNotFoundException;

import java.util.UUID;

public interface UserProfileService {

    UserRegisterResponse createUser(UserRegisterRequest request, UserRole userRole);

    UserDetailsResponse getUser(UUID userId) throws UserNotFoundException;

    UserUpdateResponse updateUser(UUID userId, UserUpdateRequest request) throws UserNotFoundException;

    void deleteUser(UUID userId);
}
