package com.example.mailguard.service.impl;

import com.example.mailguard.dto.request.UserRegisterRequest;
import com.example.mailguard.dto.request.UserUpdateRequest;
import com.example.mailguard.dto.response.UserDetailsResponse;
import com.example.mailguard.dto.response.UserRegisterResponse;
import com.example.mailguard.dto.response.UserUpdateResponse;
import com.example.mailguard.entity.UserProfile;
import com.example.mailguard.enums.UserRole;
import com.example.mailguard.exception.UserNotFoundException;
import com.example.mailguard.mapper.UserMapper;
import com.example.mailguard.repository.UserProfileRepository;
import com.example.mailguard.service.UserProfileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserProfileServiceImpl implements UserProfileService {

    private final UserProfileRepository userProfileRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserProfileServiceImpl.class);
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserProfileServiceImpl(UserProfileRepository userProfileRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userProfileRepository = userProfileRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserRegisterResponse createUser(UserRegisterRequest request, UserRole userRole) {
        UserProfile user = userMapper.userRegisterRequestToUserProfile(request);
        user.setUserRole(userRole);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user = userProfileRepository.save(user);
        LOGGER.info("User created with username: {}, role: {}", request.getUsername(), userRole.toString());
        return userMapper.userProfileToUserRegisterResponse(user);
    }


    @Override
    public UserDetailsResponse getUser(UUID userId){
        UserProfile user = userProfileRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: "+ userId));
        return userMapper.userProfileToUserDetailsResponse(user);
    }



    @Override
    public UserUpdateResponse updateUser(UUID userId, UserUpdateRequest request){
        UserProfile user = userProfileRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        boolean updated = false;

        if (user.getUsername() != null && !user.getUsername().equals(request.getUsername())){
            user.setUsername(request.getUsername());
            updated = true;
        }

        if (user.getEmail() != null && !user.getEmail().equals(request.getEmail())){
            user.setEmail(request.getEmail());
            updated = true;
        }

        if (user.getPassword() != null && !user.getPassword().equals(request.getPassword())){
            user.setPassword(request.getPassword());
            updated = true;
        }

        if (updated){
            userProfileRepository.save(user);
            LOGGER.info("User with ID {} updated successfully", userId);
        }else {
            LOGGER.info("No changes detected for user with ID {}", userId);
        }

        return userMapper.userProfileToUserUpdateResponse(user);
    }

    @Override
    public void deleteUser(UUID userId) {
        int effectedRows = userProfileRepository.deleteUser(userId);
        if (effectedRows > 0){
            LOGGER.info("User with ID {} deleted successfully", userId);
        }
    }

    @Override
    public UserProfile getUserByUsername(String username) {
        return userProfileRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found with username: "+username));
    }


}
