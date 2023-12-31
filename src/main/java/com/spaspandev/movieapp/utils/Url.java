package com.spaspandev.movieapp.utils;

import org.springframework.stereotype.Component;

@Component
public class Url {

    public static final String DAY_TRENDING_MOVIES_URL = "https://api.themoviedb.org/3/trending/movie/day";
    public static final String MOVIE_URL = "https://api.themoviedb.org/3/movie/";
    public static final String FIND_MOVIE_BY_NAME_URL = "https://api.themoviedb.org/3/search/movie?query=";
}
