package com.example.mailguard.mapper;


import com.example.mailguard.dto.request.UserRegisterRequest;
import com.example.mailguard.dto.response.UserDetailsResponse;
import com.example.mailguard.dto.response.UserRegisterResponse;
import com.example.mailguard.dto.response.UserUpdateResponse;
import com.example.mailguard.entity.UserProfile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserRegisterResponse userProfileToUserRegisterResponse(UserProfile userProfile);

    @Mapping(target = "userRole", ignore = true)
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    UserProfile userRegisterRequestToUserProfile(UserRegisterRequest request);

    UserDetailsResponse userProfileToUserDetailsResponse(UserProfile userProfile);

    UserUpdateResponse userProfileToUserUpdateResponse(UserProfile userProfile);
}
