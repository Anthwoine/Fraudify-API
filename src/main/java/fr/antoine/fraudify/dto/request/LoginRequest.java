package fr.antoine.fraudify.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record LoginRequest(
        @NotNull(message = "username is required")
        @NotBlank(message = "username cannot be blank")
        @Size(min=3, message="username must be at least 3 characters long")
        String username,

        @NotNull(message = "password is required")
        @NotBlank(message = "password cannot be blank")
        @Size(min=3, message="password must be at least 3 characters long")
        String password
) {}