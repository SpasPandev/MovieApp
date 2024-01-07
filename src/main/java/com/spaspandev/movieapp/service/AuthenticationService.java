package com.spaspandev.movieapp.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spaspandev.movieapp.config.AppUser;
import com.spaspandev.movieapp.dto.AuthenticationResponseDto;
import com.spaspandev.movieapp.dto.LoginUserDto;
import com.spaspandev.movieapp.dto.RegisterUserDto;
import com.spaspandev.movieapp.enumeration.TokenType;
import com.spaspandev.movieapp.model.entity.Token;
import com.spaspandev.movieapp.model.entity.User;
import com.spaspandev.movieapp.repository.TokenRepository;
import com.spaspandev.movieapp.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UserRepository userRepository, TokenRepository tokenRepository, PasswordEncoder passwordEncoder, JwtService jwtService,
                                 AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthenticationResponseDto register(RegisterUserDto registerUserDto) {

        User user = new User();
        user.setUsername(registerUserDto.getUsername());
        user.setEmail(registerUserDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerUserDto.getPassword()));

        User savedUser = userRepository.save(user);

        AppUser appUser = new AppUser(user);

        String jwtToken = jwtService.generateToken(appUser);

        String refreshToken = jwtService.generateRefreshToken(appUser);

        saveUserToken(savedUser, jwtToken);

        AuthenticationResponseDto authenticationResponseDto = new AuthenticationResponseDto();
        authenticationResponseDto.setAccessToken(jwtToken);
        authenticationResponseDto.setRefreshToken(refreshToken);

        return authenticationResponseDto;
    }

    public AuthenticationResponseDto login(LoginUserDto loginUserDto) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginUserDto.getUsername(), loginUserDto.getPassword()));

        User user = userRepository.findByUsername(loginUserDto.getUsername()).orElseThrow();

        AppUser appUser = new AppUser(user);

        String jwtToken = jwtService.generateToken(appUser);

        String refreshToken = jwtService.generateRefreshToken(appUser);

        revokeAllUserTokens(user);

        saveUserToken(user, jwtToken);

        AuthenticationResponseDto authenticationResponseDto = new AuthenticationResponseDto();
        authenticationResponseDto.setAccessToken(jwtToken);
        authenticationResponseDto.setRefreshToken(refreshToken);

        return authenticationResponseDto;
    }

    private void revokeAllUserTokens(User user) {

        List<Token> validUserTokens = tokenRepository.findAllValidTokensByUser(user.getId());

        if (validUserTokens.isEmpty()) {
            return;
        }

        validUserTokens.forEach(t -> {
            t.setExpired(true);
            t.setRevoked(true);
        });

        tokenRepository.saveAll(validUserTokens);
    }

    private void saveUserToken(User user, String jwtToken) {

        Token token = new Token();
        token.setUser(user);
        token.setToken(jwtToken);
        token.setTokenType(TokenType.BEARER);
        token.setRevoked(false);
        token.setExpired(false);

        tokenRepository.save(token);
    }

    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {

        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String username;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {

            return;
        }

        refreshToken = authHeader.substring(7);
        username = jwtService.extractUsername(refreshToken);

        if (username != null) {

            User user = this.userRepository.findByUsername(username).orElseThrow();

            AppUser appUser = new AppUser(user);

            if (jwtService.isTokenValid(refreshToken, appUser)) {

                String accessToken = jwtService.generateToken(appUser);

                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);

                AuthenticationResponseDto authResponse = new AuthenticationResponseDto();
                authResponse.setAccessToken(accessToken);
                authResponse.setRefreshToken(refreshToken);

                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }

}
