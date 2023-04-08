package com.adam.evaluaretehnica.security.authentication;

import com.adam.evaluaretehnica.security.authentication.http.LogInRequest;
import com.adam.evaluaretehnica.security.authentication.http.LogInResponse;
import com.adam.evaluaretehnica.security.authentication.http.RegisterRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<LogInResponse> register(
            @RequestBody @Valid RegisterRequest request
    ) {
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<LogInResponse> authenticate(
            @RequestBody @Valid LogInRequest request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }
}
