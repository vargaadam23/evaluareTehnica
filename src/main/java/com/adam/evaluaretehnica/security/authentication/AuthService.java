package com.adam.evaluaretehnica.security.authentication;

import com.adam.evaluaretehnica.exception.UserAlreadyExistsException;
import com.adam.evaluaretehnica.security.authentication.http.LogInRequest;
import com.adam.evaluaretehnica.security.authentication.http.LogInResponse;
import com.adam.evaluaretehnica.security.authentication.http.RegisterRequest;
import com.adam.evaluaretehnica.security.token.JwtService;
import com.adam.evaluaretehnica.security.token.Token;
import com.adam.evaluaretehnica.security.token.TokenRepository;
import com.adam.evaluaretehnica.user.User;
import com.adam.evaluaretehnica.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository repository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public LogInResponse register(RegisterRequest request) {
        if (repository.existsByUsername(request.getUsername())) {
            throw new UserAlreadyExistsException("A user with the same username already exists!",
                    new Exception("Username already exists in database!"));
        }

        var user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .currencyTokens(200)
                .ownedQuests(new ArrayList<>())
                .userQuests(new ArrayList<>())
                .rank(-1)
                .build();

        var savedUser = repository.save(user);

        var jwtToken = jwtService.generateToken(user);
        saveUserToken(savedUser, jwtToken);
        return LogInResponse.builder()
                .token(jwtToken)
                .build();
    }

    public LogInResponse authenticate(LogInRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        var user = repository.findByUsername(request.getUsername())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return LogInResponse.builder()
                .token(jwtToken)
                .build();
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokensByUserId(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }
}
