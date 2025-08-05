package com.example.mailguard.service.impl;

import com.example.mailguard.dto.request.UserPreferenceSaveRequest;
import com.example.mailguard.dto.request.UserPreferenceUpdateRequest;
import com.example.mailguard.dto.response.UserPreferenceResponse;
import com.example.mailguard.entity.EmailCategories;
import com.example.mailguard.entity.UserPreferences;
import com.example.mailguard.entity.UserProfile;
import com.example.mailguard.exception.UserPreferenceExistException;
import com.example.mailguard.exception.UserPreferenceNotFoundException;
import com.example.mailguard.mapper.UserPreferenceMapper;
import com.example.mailguard.repository.EmailCategoriesRepository;
import com.example.mailguard.repository.UserPreferencesRepository;
import com.example.mailguard.repository.UserProfileRepository;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;

import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class UserPreferenceServiceImplTest {

    @Mock
    private UserPreferencesRepository userPreferencesRepository;
    @Mock
    private UserProfileRepository userProfileRepository;
    @Mock
    private EmailCategoriesRepository emailCategoriesRepository;
    @Mock
    private UserPreferenceMapper userPreferenceMapper;

    @InjectMocks
    private UserPreferenceServiceImpl preferenceService;

    @Test
    @DisplayName("savePreference() should save preference and return response")
    void savePreference_ShouldSaveAndReturnResponse(){
        UUID userId = UUID.randomUUID();
        UUID categoryId = UUID.randomUUID();

        UserPreferenceSaveRequest request = new UserPreferenceSaveRequest();
        request.setConsent(true);
        request.setCategoryId(categoryId);

        UserProfile userEntity = new UserProfile();
        userEntity.setUserId(userId);

        EmailCategories categoryEntity = new EmailCategories();
        categoryEntity.setCategoryId(categoryId);

        UserPreferences preferences = new UserPreferences();
        preferences.setUser(userEntity);
        preferences.setCategory(categoryEntity);
        preferences.setConsent(request.getConsent());

        UserPreferenceResponse response = new UserPreferenceResponse();
        response.setConsent(preferences.isConsent());

        when(userPreferencesRepository.isPreferenceExists(userId, categoryId)).thenReturn(0);
        when(userProfileRepository.findById(userId)).thenReturn(Optional.of(userEntity));
        when(emailCategoriesRepository.findById(categoryId)).thenReturn(Optional.of(categoryEntity));
        when(userPreferencesRepository.save(any(UserPreferences.class))).thenReturn(preferences);
        when(userPreferenceMapper.toUserPreferenceResponse(preferences)).thenReturn(response);

        UserPreferenceResponse result = preferenceService.savePreference(userId, request);
        assertEquals(request.getConsent(), result.isConsent());

        verify(userPreferencesRepository).isPreferenceExists(userId, request.getCategoryId());
        verify(userProfileRepository).findById(userId);
        verify(emailCategoriesRepository).findById(categoryId);
        verify(userPreferencesRepository).save(any(UserPreferences.class));
    }


    @Test
    @DisplayName("savePreference() should throw exception when preference already exist")
    void savePreference_ShouldThrowException_WhenPreferenceExist(){
        UUID userId = UUID.randomUUID();
        UUID categoryId = UUID.randomUUID();

        UserPreferenceSaveRequest request = new UserPreferenceSaveRequest();
        request.setConsent(true);
        request.setCategoryId(categoryId);

        when(userPreferencesRepository.isPreferenceExists(userId, categoryId)).thenReturn(1);
        assertThrows(UserPreferenceExistException.class, () -> preferenceService.savePreference(userId, request));

        verify(userPreferencesRepository, never()).save(any());
    }

    @Test
    @DisplayName("updateConsent() should update consent and return response when consent changed")
    void updateConsent_ShouldUpdate_WhenConsentChanged(){
        UUID preferenceId = UUID.randomUUID();

        UserPreferenceUpdateRequest request = new UserPreferenceUpdateRequest();
        request.setPreferenceId(preferenceId);
        request.setConsent(true);

        UserPreferences userPreference = new UserPreferences();
        userPreference.setConsent(false);

        UserPreferenceResponse response = new UserPreferenceResponse();
        response.setConsent(true);

        when(userPreferencesRepository.findById(preferenceId)).thenReturn(Optional.of(userPreference));
        when(userPreferencesRepository.save(userPreference)).thenReturn(userPreference);
        when(userPreferenceMapper.toUserPreferenceResponse(userPreference)).thenReturn(response);

        UserPreferenceResponse result = preferenceService.updateConsent(request);

        assertEquals(request.getConsent(), result.isConsent());
        verify(userPreferencesRepository).save(userPreference);
    }

    @Test
    @DisplayName("updateConsent() should throw exception when preference not found")
    void updateConsent_ShouldThrowException_WhenPrefNotFound(){
        UUID preferenceId = UUID.randomUUID();

        UserPreferenceUpdateRequest request = new UserPreferenceUpdateRequest();
        request.setPreferenceId(preferenceId);
        request.setConsent(true);

        when(userPreferencesRepository.findById(preferenceId)).thenReturn(Optional.empty());

        assertThrows(UserPreferenceNotFoundException.class,
                () -> preferenceService.updateConsent(request)
        );

        verify(userPreferencesRepository, never()).save(any());
    }
}
