package com.spaspandev.movieapp.dto;

import jakarta.validation.constraints.NotNull;

public class EditMovieDto {

    @NotNull
    private Long movie_external_id;
    @NotNull
    private String imdb_id;
    @NotNull
    private String name;
    @NotNull
    private String description;
    @NotNull
    private int budget;
    @NotNull
    private int revenue;
    @NotNull
    private int runtime;
    @NotNull
    private double popularity;
    @NotNull
    private String status;

    public EditMovieDto() {
    }

    public Long getMovie_external_id() {
        return movie_external_id;
    }

    public void setMovie_external_id(Long movie_external_id) {
        this.movie_external_id = movie_external_id;
    }

    public String getImdb_id() {
        return imdb_id;
    }

    public void setImdb_id(String imdb_id) {
        this.imdb_id = imdb_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public int getRevenue() {
        return revenue;
    }

    public void setRevenue(int revenue) {
        this.revenue = revenue;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
