package com.spaspandev.movieapp.service;

import com.spaspandev.movieapp.config.AppUser;
import com.spaspandev.movieapp.dto.AuthenticationResponseDto;
import com.spaspandev.movieapp.dto.LoginUserDto;
import com.spaspandev.movieapp.dto.RegisterUserDto;
import com.spaspandev.movieapp.enumeration.TokenType;
import com.spaspandev.movieapp.model.entity.Token;
import com.spaspandev.movieapp.model.entity.User;
import com.spaspandev.movieapp.repository.TokenRepository;
import com.spaspandev.movieapp.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

        saveUserToken(savedUser, jwtToken);

        AuthenticationResponseDto authenticationResponseDto = new AuthenticationResponseDto();
        authenticationResponseDto.setAccessToken(jwtToken);

        return authenticationResponseDto;
    }

    public ResponseEntity<?> login(LoginUserDto loginUserDto) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginUserDto.getUsername(), loginUserDto.getPassword()));

        User user = userRepository.findByUsername(loginUserDto.getUsername()).orElseThrow();

        if(user.isDeleted()){
            return ResponseEntity.notFound().build();
        }

        AppUser appUser = new AppUser(user);

        String jwtToken = jwtService.generateToken(appUser);

        revokeAllUserTokens(user);

        saveUserToken(user, jwtToken);

        AuthenticationResponseDto authenticationResponseDto = new AuthenticationResponseDto();
        authenticationResponseDto.setAccessToken(jwtToken);

        return ResponseEntity.ok(authenticationResponseDto);
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


}
