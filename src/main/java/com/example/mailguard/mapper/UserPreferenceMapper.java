package com.example.mailguard.mapper;

import com.example.mailguard.dto.response.UserPreferenceResponse;
import com.example.mailguard.dto.response.UserPreferenceWithUser;
import com.example.mailguard.entity.UserPreferences;
import jakarta.mail.MailSessionDefinition;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserPreferenceMapper {

    @Mapping(source = "userPreferences.category.categoryName", target = "categoryName")
    @Mapping(source = "userPreferences.category.description", target = "categoryDescription")
    @Mapping(source = "userPreferences.createdAt", target = "createdAt")
    @Mapping(source = "userPreferences.updatedAt", target = "updatedAt")
    UserPreferenceResponse toUserPreferenceResponse(UserPreferences userPreferences);

    @Mapping(source = "userPreferences.user.username", target = "username")
    @Mapping(source = "userPreferences.category.categoryName", target = "categoryName")
    UserPreferenceWithUser toUserPreferenceWithUser(UserPreferences userPreferences);
}
