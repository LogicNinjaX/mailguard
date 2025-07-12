package com.example.mailguard.mapper;

import com.example.mailguard.dto.request.EmailCategoryRegisterRequest;
import com.example.mailguard.dto.request.EmailCategoryUpdateRequest;
import com.example.mailguard.dto.response.EmailCategoryResponse;
import com.example.mailguard.entity.EmailCategories;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EmailCategoryMapper {

    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "categoryId", ignore = true)
    @Mapping(target = "active", ignore = true)
    EmailCategories registerRequestToEmailCategories(EmailCategoryRegisterRequest request);

    EmailCategoryResponse emailCategoriesToResponse(EmailCategories emailCategories);

}
