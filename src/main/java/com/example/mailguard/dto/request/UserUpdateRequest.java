package com.example.mailguard.dto.request;


import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public class UserUpdateRequest {

    @NotBlank(message = "${user.username.not-blank}")
    @Length(min = 5, max = 20, message = "Username must be between {min} and {max}")
    private String username;

    @NotBlank(message = "${user.password.not-blank}")
    @Length(min = 8, max = 20, message = "Password must be between {min} and {max}")
    private String password;

    @NotBlank(message = "${user.email.not-blank}")
    private String email;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
