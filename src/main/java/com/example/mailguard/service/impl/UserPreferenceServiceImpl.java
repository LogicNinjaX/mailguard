package com.example.mailguard.service.impl;

import com.example.mailguard.dto.request.UserPreferenceSaveRequest;
import com.example.mailguard.dto.request.UserPreferenceUpdateRequest;
import com.example.mailguard.dto.response.UserPreferenceResponse;
import com.example.mailguard.entity.EmailCategories;
import com.example.mailguard.entity.UserPreferences;
import com.example.mailguard.entity.UserProfile;
import com.example.mailguard.exception.CategoryNotFoundException;
import com.example.mailguard.exception.UserNotFoundException;
import com.example.mailguard.exception.UserPreferenceExistException;
import com.example.mailguard.exception.UserPreferenceNotFoundException;
import com.example.mailguard.mapper.UserPreferenceMapper;
import com.example.mailguard.repository.EmailCategoriesRepository;
import com.example.mailguard.repository.UserPreferencesRepository;
import com.example.mailguard.repository.UserProfileRepository;
import com.example.mailguard.service.UserPreferenceService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.UUID;

public class UserPreferenceServiceImpl implements UserPreferenceService {

    private final UserPreferencesRepository userPreferencesRepository;
    private final UserProfileRepository userProfileRepository;
    private final EmailCategoriesRepository emailCategoriesRepository;
    private final UserPreferenceMapper userPreferenceMapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserPreferenceServiceImpl.class);

    public UserPreferenceServiceImpl(UserPreferencesRepository userPreferencesRepository, UserProfileRepository userProfileRepository, EmailCategoriesRepository emailCategoriesRepository, UserPreferenceMapper userPreferenceMapper) {
        this.userPreferencesRepository = userPreferencesRepository;
        this.userProfileRepository = userProfileRepository;
        this.emailCategoriesRepository = emailCategoriesRepository;
        this.userPreferenceMapper = userPreferenceMapper;
    }


    @Transactional
    @Override
    public UserPreferenceResponse savePreference(UUID userId, UserPreferenceSaveRequest request){
        int count = userPreferencesRepository.isPreferenceExists(userId, request.getCategoryId());

        if (count > 0){
            LOGGER.warn("Preference already exists for userId={} and categoryId={}", userId, request.getCategoryId());
            throw new UserPreferenceExistException("User preference already exists");
        }

        UserProfile user = userProfileRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: "+userId));

        EmailCategories emailCategory = emailCategoriesRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new CategoryNotFoundException("Email category not found with id: "+request.getCategoryId()));

        UserPreferences userPreference = new UserPreferences();
        userPreference.setUser(user);
        userPreference.setCategory(emailCategory);
        userPreference.setConsent(request.getConsent());

        UserPreferences savedEntity = savedEntity = userPreferencesRepository.save(userPreference);

        LOGGER.info("User preference saved successfully for userId={}, categoryId={}", userId, request.getCategoryId());
        return userPreferenceMapper.toUserPreferenceResponse(userPreference);
    }

    @Override
    public UserPreferenceResponse updateConsent(UserPreferenceUpdateRequest request){
        UserPreferences userPreference = userPreferencesRepository.findById(request.getPreferenceId())
                .orElseThrow(() -> new UserPreferenceNotFoundException("User User preference not found with id: "+request.getPreferenceId()));

        if (userPreference.isConsent() != request.getConsent()){
            userPreference.setConsent(request.getConsent());
            userPreference = userPreferencesRepository.save(userPreference);
            LOGGER.info("User consent updated successfully for preferenceId={}", request.getPreferenceId());
        }else {
            LOGGER.info("No change in consent value for preferenceId={}", request.getPreferenceId());
        }

        return userPreferenceMapper.toUserPreferenceResponse(userPreference);
    }

    @Override
    public List<UserPreferenceResponse> getUserPreferences(UUID userId, int page, int size, String sortBy, String sortDir){

        Sort sort = sortDir.equalsIgnoreCase("desc") ?
                Sort.by(Sort.Direction.DESC, sortBy) : Sort.by(Sort.Direction.ASC, sortBy);

        Pageable pageable = PageRequest.of(page, size, sort);

        return userPreferencesRepository.findByUserId(userId, pageable)
                .getContent()
                .stream()
                .map(userPreferenceMapper::toUserPreferenceResponse)
                .toList();
    }
}
