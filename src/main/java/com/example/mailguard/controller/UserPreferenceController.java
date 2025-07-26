package com.example.mailguard.controller;

import com.example.mailguard.dto.request.UserPreferenceSaveRequest;
import com.example.mailguard.dto.request.UserPreferenceUpdateRequest;
import com.example.mailguard.dto.response.ApiResponse;
import com.example.mailguard.dto.response.UserPreferenceResponse;
import com.example.mailguard.security.CustomUserDetails;
import com.example.mailguard.service.UserPreferenceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "User Preference Management", description = "Operations related to user preference")
@SecurityRequirement(name = "bearerAuth")
public class UserPreferenceController {

    private final UserPreferenceService userPreferenceService;


    public UserPreferenceController(UserPreferenceService userPreferenceService) {
        this.userPreferenceService = userPreferenceService;
    }


    @PostMapping(value = "/preferences", consumes = "application/json", produces = "application/json")
    @Operation(summary = "Save use preference", description = "Returns saved preference details")
    public ResponseEntity<ApiResponse<UserPreferenceResponse>> savePreference(
            @AuthenticationPrincipal CustomUserDetails user,
            @Valid @RequestBody UserPreferenceSaveRequest request
    )
    {
        UserPreferenceResponse savedEntity = userPreferenceService.savePreference(user.getUserId(), request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "user preference saved successfully", savedEntity));
    }

    @GetMapping(value = "/preferences", produces = "application/json")
    @Operation(summary = "Get user preferences", description = "Returns list of user preferences of current user")
    public ResponseEntity<ApiResponse<List<UserPreferenceResponse>>> getUserPreferences(
            @AuthenticationPrincipal CustomUserDetails user,
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(required = false, defaultValue = "category") String sortBy,
            @RequestParam(required = false, defaultValue = "asc") String sortDir
    )
    {
        List<UserPreferenceResponse> preferenceList = userPreferenceService
                .getUserPreferences(user.getUserId(), page, size, sortBy, sortDir);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(true, "request successful", preferenceList));
    }

    @PutMapping(value = "/preferences", consumes = "application/json", produces = "application/json")
    @Operation(summary = "Update user preference", description = "Returns update details")
    public ResponseEntity<ApiResponse<UserPreferenceResponse>> updatePreference(@Valid @RequestBody UserPreferenceUpdateRequest request){
        UserPreferenceResponse response = userPreferenceService.updateConsent(request);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(true, "user preference updated successfully", response));
    }

}
