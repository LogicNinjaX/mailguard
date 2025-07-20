package com.example.mailguard.controller;


import com.example.mailguard.dto.request.UserUpdateRequest;
import com.example.mailguard.dto.response.ApiResponse;
import com.example.mailguard.dto.response.UserDetailsResponse;
import com.example.mailguard.dto.response.UserUpdateResponse;
import com.example.mailguard.security.CustomUserDetails;
import com.example.mailguard.service.UserProfileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1")
public class UserController {

    private final UserProfileService userProfileService;

    public UserController(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }


    @PutMapping("/users")
    public ResponseEntity<ApiResponse<UserUpdateResponse>> updateUserDetails(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody UserUpdateRequest request){
        UserUpdateResponse response = userProfileService.updateUser(userDetails.getUserId(), request);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(true, "user details updated successfully", response));
    }

    @GetMapping("/users")
    public ResponseEntity<ApiResponse<UserDetailsResponse>> getUser(@AuthenticationPrincipal CustomUserDetails userDetails){
        UserDetailsResponse response = userProfileService.getUser(userDetails.getUserId());
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(true, "user retrieved successfully", response));
    }

    @DeleteMapping("/users")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@AuthenticationPrincipal CustomUserDetails userDetails){
        userProfileService.deleteUser(userDetails.getUserId());
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(new ApiResponse<>(true, "user deleted successfully", null));
    }

}
