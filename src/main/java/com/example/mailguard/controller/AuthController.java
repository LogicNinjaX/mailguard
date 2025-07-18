package com.example.mailguard.controller;

import com.example.mailguard.dto.request.UserLoginRequest;
import com.example.mailguard.dto.request.UserRegisterRequest;
import com.example.mailguard.dto.response.ApiResponse;
import com.example.mailguard.dto.response.UserRegisterResponse;
import com.example.mailguard.entity.UserProfile;
import com.example.mailguard.enums.UserRole;
import com.example.mailguard.service.UserProfileService;
import com.example.mailguard.util.JwtTokenUtil;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final UserProfileService userProfileService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;

    public AuthController(UserProfileService userProfileService, AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil) {
        this.userProfileService = userProfileService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserRegisterResponse>> registerUser(@Valid @RequestBody UserRegisterRequest request){
        UserRegisterResponse savedUser = userProfileService.createUser(request, UserRole.USER);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "user registration successful", savedUser));
    }


    @PostMapping("/login")
    public ResponseEntity<ApiResponse<Map<String, String>>> login(@Valid @RequestBody UserLoginRequest request){
        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        UserProfile user = userProfileService.getUserByUsername(userDetails.getUsername());

        String token = jwtTokenUtil.generateToken(userDetails, user.getUserId(), user.getUserRole().name());

        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "login successful"
                        , Collections.singletonMap("token", token)
        ));
    }

}
