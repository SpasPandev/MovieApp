package com.spaspandev.movieapp.dto;

public class MovieDto {

    private int id;
    private String original_title;
    private String original_name;
    private String overview;

    public MovieDto() {
    }

    public int getId() {
        return id;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public String getOriginal_name() {
        return original_name;
    }

    public String getOverview() {
        return overview;
    }
}
