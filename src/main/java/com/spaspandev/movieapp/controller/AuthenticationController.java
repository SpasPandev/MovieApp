package com.spaspandev.movieapp.controller;

import com.spaspandev.movieapp.dto.LoginUserDto;
import com.spaspandev.movieapp.dto.AuthenticationResponseDto;
import com.spaspandev.movieapp.service.AuthenticationService;
import com.spaspandev.movieapp.dto.RegisterUserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<?> login(@RequestBody LoginUserDto loginUserDto) {

        return authenticationService.login(loginUserDto);
    }

}
