package com.spaspandev.movieapp.dto;

public class AuthenticationResponseDto {

    private String accessToken;

    public AuthenticationResponseDto() {
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

}
