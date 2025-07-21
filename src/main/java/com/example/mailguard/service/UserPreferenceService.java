package com.example.mailguard.service;

import com.example.mailguard.dto.request.UserPreferenceSaveRequest;
import com.example.mailguard.dto.request.UserPreferenceUpdateRequest;
import com.example.mailguard.dto.response.UserPreferenceResponse;
import com.example.mailguard.exception.CategoryNotFoundException;
import com.example.mailguard.exception.UserNotFoundException;
import com.example.mailguard.exception.UserPreferenceExistException;
import com.example.mailguard.exception.UserPreferenceNotFoundException;

import java.util.List;
import java.util.UUID;

public interface UserPreferenceService {

    UserPreferenceResponse savePreference(UUID userId, UserPreferenceSaveRequest request) throws UserPreferenceExistException, UserNotFoundException, CategoryNotFoundException;

    UserPreferenceResponse updateConsent(UserPreferenceUpdateRequest request) throws UserPreferenceNotFoundException;

    List<UserPreferenceResponse> getUserPreferences(UUID userId, int page, int size, String sortBy, String sortDir);
}
