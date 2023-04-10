package com.adam.evaluaretehnica.security.authentication;

import com.adam.evaluaretehnica.http.ETResponse;
import com.adam.evaluaretehnica.security.authentication.http.LogInRequest;
import com.adam.evaluaretehnica.security.authentication.http.LogInResponse;
import com.adam.evaluaretehnica.security.authentication.http.RegisterRequest;
import com.adam.evaluaretehnica.user.http.UserResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService service;

    @PostMapping("/register")
    public ResponseEntity<ETResponse> register(
            @RequestBody @Valid RegisterRequest request
    ) {
        return ResponseEntity.ok(
                new ETResponse(
                        HttpStatus.OK,
                        "/auth/register",
                        service.register(request))
                );
    }

    @PostMapping("/login")
    public ResponseEntity<ETResponse> authenticate(
            @RequestBody @Valid LogInRequest request
    ) {
        return ResponseEntity.ok(
                new ETResponse(
                        HttpStatus.OK,
                        "/auth/login",
                        service.authenticate(request))
        );
    }
}
