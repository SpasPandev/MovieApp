package com.spaspandev.movieapp.controller;

import com.spaspandev.movieapp.dto.LoginUserDto;
import com.spaspandev.movieapp.dto.AuthenticationResponseDto;
import com.spaspandev.movieapp.service.AuthenticationService;
import com.spaspandev.movieapp.dto.RegisterUserDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponseDto> register(@RequestBody RegisterUserDto registerUserDto) {

        return ResponseEntity.ok(authenticationService.register(registerUserDto));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponseDto> login(@RequestBody LoginUserDto loginUserDto) {

        return ResponseEntity.ok(authenticationService.login(loginUserDto));
    }

    @PostMapping("/refresh-token")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {

        authenticationService.refreshToken(request, response);
    }
}
