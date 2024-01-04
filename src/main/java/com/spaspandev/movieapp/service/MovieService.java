package com.spaspandev.movieapp.service;

import com.spaspandev.movieapp.dto.DayTrendingMoviesDto;
import com.spaspandev.movieapp.utils.Url;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MovieService {

    private final RestTemplate restTemplate;

    @Value("${api_key}")
    private String api;

    public MovieService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public DayTrendingMoviesDto getDayTrendingMovies() {

        return restTemplate.getForObject(Url.ALL_DAY_TRENDING_MOVIES_URL + "?api_key=" + api, DayTrendingMoviesDto.class);
    }
}
