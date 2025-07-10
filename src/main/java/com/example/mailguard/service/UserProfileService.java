package com.example.mailguard.service;

import com.example.mailguard.dto.request.UserRegisterRequest;
import com.example.mailguard.dto.response.UserRegisterResponse;
import com.example.mailguard.enums.UserRole;

public interface UserProfileService {

    UserRegisterResponse createUser(UserRegisterRequest request, UserRole userRole);
}
