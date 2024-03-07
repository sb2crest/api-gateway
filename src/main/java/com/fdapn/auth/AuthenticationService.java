package com.fdapn.auth;

import com.fdapn.config.JwtService;
import com.fdapn.dao.TokenRepository;
import com.fdapn.dao.UserRepository;
import com.fdapn.exception.InvalidPasswordException;
import com.fdapn.exception.NotFoundException;
import com.fdapn.exception.UserAlreadyExistsException;
import com.fdapn.model.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    public AuthenticationResponse register(RegisterRequest request) {
        if (userExists(request)) {
            throw new UserAlreadyExistsException("User already exists. Please login.");
        }
        if (!passwordValid(request.getPassword())) {
            throw new InvalidPasswordException("Please enter valid password.");
        }
        Role userRole = request.getRole() != null ? request.getRole() : Role.USER;
        User user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .userId(validateUserId(request.getUserId()))
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(userRole)
                .build();
        User savedUser = repository.save(user);
        TokenDetails jwtToken = jwtService.generateToken(user);
        TokenDetails refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(savedUser, jwtToken.getToken());
        String tokenExpiration = jwtToken.getExpirationTime();
        String refreshTokenExpiration = refreshToken.getExpirationTime();
        return AuthenticationResponse.builder()
                .userId(user.getUserId())
                .accessToken(jwtToken.getToken())
                .refreshToken(refreshToken.getToken())
                .tokenExpired(tokenExpiration)
                .refreshTokenExpired(refreshTokenExpiration)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        User user = repository.findByEmail(request.getEmail())
                .orElseThrow(() -> new NotFoundException("User not found with email  - {}: " + request.getEmail()));

        TokenDetails jwtToken = jwtService.generateToken(user);
        TokenDetails refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken.getToken());
        String tokenExpiration = jwtToken.getExpirationTime();
        String refreshTokenExpiration = refreshToken.getExpirationTime();
        return AuthenticationResponse.builder()
                .userId(user.getUserId())
                .accessToken(jwtToken.getToken())
                .refreshToken(refreshToken.getToken())
                .tokenExpired(tokenExpiration)
                .refreshTokenExpired(refreshTokenExpiration)
                .build();
    }

    private void saveUserToken(User user, String jwtToken) {
        Token token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        List<Token> validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail != null) {
            User user = this.repository.findByEmail(userEmail)
                    .orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                TokenDetails accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken.getToken());
                var authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken.getToken())
                        .refreshToken(refreshToken)
                        .tokenExpired(accessToken.getExpirationTime())
                        .refreshTokenExpired("")
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }
    public boolean passwordValid(String password) {
        return password != null && password.length() >= 8 &&
                password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()_+\\[\\]{};':\"\\\\|,.<>/?]).{8,}$");
    }
    private boolean userExists(RegisterRequest request) {
        return repository.findByEmail(request.getEmail()).isPresent();
    }
    public String validateUserId(String userId) throws IllegalArgumentException {
        if (userId == null || !userId.matches("^[a-zA-Z0-9]{10}$")) {
            throw new IllegalArgumentException("Invalid User ID. The field should contain 10 alphanumeric characters.");
        }
        return userId;
    }

}
