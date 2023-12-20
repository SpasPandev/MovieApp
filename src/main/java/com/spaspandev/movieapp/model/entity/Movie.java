package com.spaspandev.movieapp.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class Movie extends BaseEntity{
    @Column(nullable = false, unique = true)
    private Long movie_external_id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String description;


    public Movie() {
    }

    public Long getMovie_external_id() {
        return movie_external_id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
