package com.adam.evaluaretehnica.security.authentication.http;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public record RegisterRequest(
        @NotEmpty
        @Size(min = 4, max = 20, message = "Username must be between 4 and 20 characters")
        String username,
        @NotEmpty
        @Size(min = 4, max = 20, message = "Password must be between 4 and 20 characters")
        String password
) {}
