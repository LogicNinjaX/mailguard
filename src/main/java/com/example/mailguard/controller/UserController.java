package com.example.mailguard.controller;


import com.example.mailguard.dto.request.UserUpdateRequest;
import com.example.mailguard.dto.response.ApiResponse;
import com.example.mailguard.dto.response.UserDetailsResponse;
import com.example.mailguard.dto.response.UserUpdateResponse;
import com.example.mailguard.security.CustomUserDetails;
import com.example.mailguard.service.UserProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1")
@Tag(name = "User Management", description = "Operations related to users")
@SecurityRequirement(name = "bearerAuth")
public class UserController {

    private final UserProfileService userProfileService;

    public UserController(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }


    @PutMapping(value = "/users", consumes = "application/json", produces = "application/json")
    @Operation(summary = "Update user details", description = "Returns updated user details")
    public ResponseEntity<ApiResponse<UserUpdateResponse>> updateUserDetails(@AuthenticationPrincipal CustomUserDetails userDetails, @Valid @RequestBody UserUpdateRequest request){
        UserUpdateResponse response = userProfileService.updateUser(userDetails.getUserId(), request);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(true, "user details updated successfully", response));
    }

    @GetMapping(value = "/users", produces = "application/json")
    @Operation(summary = "Get user details", description = "Returns user data")
    public ResponseEntity<ApiResponse<UserDetailsResponse>> getUser(@AuthenticationPrincipal CustomUserDetails userDetails){
        UserDetailsResponse response = userProfileService.getUser(userDetails.getUserId());
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(true, "user retrieved successfully", response));
    }

    @DeleteMapping(value = "/users")
    @Operation(summary = "Delete user")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@AuthenticationPrincipal CustomUserDetails userDetails){
        userProfileService.deleteUser(userDetails.getUserId());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
