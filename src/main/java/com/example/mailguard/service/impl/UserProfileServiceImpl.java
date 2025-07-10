package com.example.mailguard.service.impl;

import com.example.mailguard.dto.request.UserRegisterRequest;
import com.example.mailguard.dto.response.UserRegisterResponse;
import com.example.mailguard.entity.UserProfile;
import com.example.mailguard.enums.UserRole;
import com.example.mailguard.repository.UserProfileRepository;
import com.example.mailguard.service.UserProfileService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class UserProfileServiceImpl implements UserProfileService {

    private final UserProfileRepository userProfileRepository;
    private final ModelMapper modelMapper;

    public UserProfileServiceImpl(UserProfileRepository userProfileRepository, ModelMapper modelMapper) {
        this.userProfileRepository = userProfileRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserRegisterResponse createUser(UserRegisterRequest request, UserRole userRole) {
        var user = modelMapper.map(request, UserProfile.class);
        user = userProfileRepository.save(user);
        return modelMapper.map(user, UserRegisterResponse.class);
    }
}
