package com.spaspandev.movieapp.model.entity;

import com.enumeration.Role;
import jakarta.persistence.*;

import java.util.Set;

@Entity
public class User extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false, unique = true)
    private String email;
    @Enumerated(EnumType.STRING)
    private Role role = Role.ROLE_USER;
    @ManyToMany
    @JoinTable(name = "watchlist", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "movie_id"))
    private Set<Movie> watchlist;
    @ManyToMany
    @JoinTable(name = "likedMovies", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "movie_id"))
    private Set<Movie> likedMovies;

    public User() {
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public Role getRole() {
        return role;
    }

    public Set<Movie> getWatchlist() {
        return watchlist;
    }

    public Set<Movie> getLikedMovies() {
        return likedMovies;
    }
}