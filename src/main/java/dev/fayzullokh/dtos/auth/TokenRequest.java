package dev.fayzullokh.dtos.auth;

import jakarta.validation.ValidationException;
import jakarta.validation.constraints.NotBlank;

public record TokenRequest(@NotBlank String username, @NotBlank String password) {
    public TokenRequest {
        // Add debug/log statements here to check if this block is executed.
        if (username == null || username.trim().isEmpty()) {
            throw new ValidationException("Username must not be blank");
        }
        // Other validation logic...
    }
}
