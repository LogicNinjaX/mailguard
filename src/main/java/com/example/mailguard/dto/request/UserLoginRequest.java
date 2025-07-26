package com.example.mailguard.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "User login request")
public class UserLoginRequest {

    @Schema(description = "Username", example = "james")
    @NotBlank(message = "user.username.not-blank")
    private String username;

    @Schema(description = "Password", example = "james123")
    @NotBlank(message = "user.password.not-blank")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
