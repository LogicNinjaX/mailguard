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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class UseProfileServiceImplTest {

    @Mock
    private UserProfileRepository userProfileRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserProfileServiceImpl userProfileService;

    @Test
    @DisplayName("createUser() should create user and return user")
    void createUser_ShouldReturnResponse(){
        UserRegisterRequest request = new UserRegisterRequest();
        request.setUsername("nitish");
        request.setEmail("example@gmail.com");
        request.setPassword("nitish12345678");

        UserProfile mappedUser = new UserProfile();
        mappedUser.setUsername("nitish");
        mappedUser.setEmail("example@gmail.com");

        UserProfile savedUser = new UserProfile();
        savedUser.setUsername("nitish");
        savedUser.setUserId(UUID.randomUUID());

        UserRegisterResponse expectedResponse = new UserRegisterResponse();
        expectedResponse.setUsername("nitish");

        Mockito.when(userMapper.userRegisterRequestToUserProfile(request)).thenReturn(mappedUser);
        Mockito.when(passwordEncoder.encode(request.getPassword())).thenReturn(request.getPassword());
        Mockito.when(userProfileRepository.save(Mockito.any(UserProfile.class))).thenReturn(savedUser);
        Mockito.when(userMapper.userProfileToUserRegisterResponse(savedUser)).thenReturn(expectedResponse);

        UserRegisterResponse response = userProfileService.createUser(request, UserRole.USER);

        Assertions.assertEquals("nitish", response.getUsername());
        Mockito.verify(userProfileRepository).save(mappedUser);

    }


    @Test
    @DisplayName("getUser() should return valid response when valid user id provided")
    void getUser_ShouldReturnValidResponse_WhenIdProvided(){
        UUID userId = UUID.randomUUID();

        UserProfile user = new UserProfile();
        user.setUserId(userId);
        user.setUsername("nitish");

        UserDetailsResponse response = new UserDetailsResponse();
        response.setUsername("nitish");

        Mockito.when(userProfileRepository.findById(userId)).thenReturn(Optional.of(user));
        Mockito.when(userMapper.userProfileToUserDetailsResponse(user)).thenReturn(response);

        UserDetailsResponse result = userProfileService.getUser(userId);

        Assertions.assertEquals("nitish", result.getUsername());
    }

    @Test
    @DisplayName("getUser() should throw exception when user not found")
    void getUser_ShouldThrowException(){
        UUID userId = UUID.randomUUID();
        Mockito.when(userProfileRepository.findById(userId)).thenReturn(Optional.empty());
        Assertions.assertThrows(UserNotFoundException.class, () -> userProfileService.getUser(userId));
    }

    @Test
    @DisplayName("updateUser() should update username and return updated response")
    void updateUser_shouldUpdateUsername_WhenUsernameProvided(){
        UUID userId = UUID.randomUUID();

        UserUpdateRequest request = new UserUpdateRequest();
        request.setUsername("nitish");

        UserProfile user = new UserProfile();
        user.setUserId(userId);
        user.setUsername("rohit");

        UserUpdateResponse response = new UserUpdateResponse();
        response.setUsername("nitish");

        Mockito.when(userProfileRepository.findById(userId)).thenReturn(Optional.of(user));
        Mockito.when(userMapper.userProfileToUserUpdateResponse(user)).thenReturn(response);

        UserUpdateResponse result = userProfileService.updateUser(userId, request);
        Assertions.assertEquals("nitish", result.getUsername());
        Assertions.assertEquals("nitish", user.getUsername());

        Mockito.verify(userProfileRepository).save(user);
    }

    @Test
    @DisplayName("deleteUser() should log message of successful deletion")
    void deleteUser_ShouldLogDeletion(){
        UUID userId = UUID.randomUUID();
        Mockito.when(userProfileRepository.deleteUser(userId)).thenReturn(1);
        userProfileService.deleteUser(userId);
        Mockito.verify(userProfileRepository).deleteUser(userId);
    }

    @Test
    @DisplayName("getUserByUsername() should return user when user id is valid")
    void getUserByUsername_ShouldReturnUser(){
        String username = "nitish";

        UserProfile user = new UserProfile();
        user.setUsername(username);

        Mockito.when(userProfileRepository.findByUsername(username)).thenReturn(Optional.of(user));

        UserProfile result = userProfileService.getUserByUsername(username);
        Assertions.assertEquals(username, result.getUsername());
    }

    @Test
    @DisplayName("getUserByUsername() should throw exception when user not found")
    void getUserByUsername_ShouldThrowException(){
        String username = "nitish";
        Mockito.when(userProfileRepository.findByUsername(username)).thenReturn(Optional.empty());
        Assertions
                .assertThrows(UserNotFoundException.class, () -> userProfileService.getUserByUsername(username));
    }
}
