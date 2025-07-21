package com.example.mailguard.controller;

import com.example.mailguard.dto.request.UserPreferenceSaveRequest;
import com.example.mailguard.dto.request.UserPreferenceUpdateRequest;
import com.example.mailguard.dto.response.ApiResponse;
import com.example.mailguard.dto.response.UserPreferenceResponse;
import com.example.mailguard.security.CustomUserDetails;
import com.example.mailguard.service.UserPreferenceService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class UserPreferenceController {

    private final UserPreferenceService userPreferenceService;


    public UserPreferenceController(UserPreferenceService userPreferenceService) {
        this.userPreferenceService = userPreferenceService;
    }


    @PostMapping("/preferences")
    public ResponseEntity<ApiResponse<UserPreferenceResponse>> savePreference(
            @AuthenticationPrincipal CustomUserDetails user,
            @Valid @RequestBody UserPreferenceSaveRequest request
    )
    {
        UserPreferenceResponse savedEntity = userPreferenceService.savePreference(user.getUserId(), request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "user preference saved successfully", savedEntity));
    }

    @GetMapping("/preferences")
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

    @PutMapping("/preferences")
    public ResponseEntity<ApiResponse<UserPreferenceResponse>> updatePreference(@Valid @RequestBody UserPreferenceUpdateRequest request){
        UserPreferenceResponse response = userPreferenceService.updateConsent(request);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(true, "user preference updated successfully", response));
    }

}
