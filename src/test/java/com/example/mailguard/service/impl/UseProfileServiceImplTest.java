package com.example.mailguard.service.impl;

import com.example.mailguard.dto.request.UserRegisterRequest;
import com.example.mailguard.dto.response.UserDetailsResponse;
import com.example.mailguard.dto.response.UserRegisterResponse;
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
    void getUser_ShouldThrowException(){
        UUID userId = UUID.randomUUID();
        Mockito.when(userProfileRepository.findById(userId)).thenReturn(Optional.empty());
        Assertions.assertThrows(UserNotFoundException.class, () -> userProfileService.getUser(userId));
    }
}
