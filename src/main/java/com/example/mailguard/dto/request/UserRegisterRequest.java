package com.example.mailguard.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

@Schema(description = "User register request")
public class UserRegisterRequest {

    @Schema(description = "Username", example = "james")
    @NotBlank(message = "${user.username.not-blank}")
    @Length(min = 5, max = 20, message = "Username must be between {min} and {max}")
    private String username;

    @Schema(description = "Password", example = "james123")
    @NotBlank(message = "${user.password.not-blank}")
    @Length(min = 8, max = 20, message = "Password must be between {min} and {max}")
    private String password;

    @Schema(description = "User email", example = "james@example.com")
    @NotBlank(message = "${user.email.not-blank}")
    @Email(message = "User email: ${validatedEmail} is not valid")
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
