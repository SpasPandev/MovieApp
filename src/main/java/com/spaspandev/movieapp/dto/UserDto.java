package com.spaspandev.movieapp.dto;

import com.spaspandev.movieapp.enumeration.Role;
import com.spaspandev.movieapp.model.entity.Movie;
import jakarta.persistence.*;

import java.util.Set;

public class UserDto {

    private String username;
    private String email;
    private Role role;
    private boolean isDeleted;

    public UserDto() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
}
