package com.adam.evaluaretehnica.security.authentication.http;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LogInRequest {
    @NotEmpty
    private String username;
    @NotEmpty
    @Size(min = 4, max = 20, message = "Password must be between 4 and 20 characters")
    private String password;
}
