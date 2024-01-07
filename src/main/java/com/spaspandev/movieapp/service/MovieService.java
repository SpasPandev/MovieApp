package com.spaspandev.movieapp.service;

import com.spaspandev.movieapp.dto.*;
import com.spaspandev.movieapp.model.entity.Movie;
import com.spaspandev.movieapp.repository.MovieRepository;
import com.spaspandev.movieapp.utils.Url;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MovieService {

    private final RestTemplate restTemplate;
    private final MovieRepository movieRepository;
    private final ModelMapper modelMapper;

    @Value("${api_key}")
    private String api;

    public MovieService(RestTemplate restTemplate, MovieRepository movieRepository, ModelMapper modelMapper) {
        this.restTemplate = restTemplate;
        this.movieRepository = movieRepository;
        this.modelMapper = modelMapper;
    }

    public DayTrendingMoviesDto getDayTrendingMovies() {

        DayTrendingMoviesDto dayTrendingMoviesDto = restTemplate.getForObject(Url.DAY_TRENDING_MOVIES_URL +
                "?api_key=" + api, DayTrendingMoviesDto.class);

        dayTrendingMoviesDto.getResults().forEach(this::checkIfMovieExistInDbAndSaveIt);

        return dayTrendingMoviesDto;
    }

    private void checkIfMovieExistInDbAndSaveIt(MovieSmallInfoDto movieSmallInfoDto) {

        if(movieRepository.findByMovieExternalId(movieSmallInfoDto.getId()).isEmpty()){

            MovieFullInfoDto movieFullInfoDto = restTemplate.getForObject(Url.MOVIE_URL + movieSmallInfoDto.getId() +
                    "?api_key=" + api, MovieFullInfoDto.class);

            Movie movie = modelMapper.map(movieFullInfoDto, Movie.class);

            movieRepository.save(movie);
        }
    }

    public ResponseEntity<?> deleteMovie(Long id) {

        if (movieRepository.findById(id).isPresent()) {

            movieRepository.deleteById(id);
            return ResponseEntity.ok("Movie with id: " + id + " is deleted!");
        } else {

            return new ResponseEntity<>("Movie with id: " + id + " was not found!", HttpStatus.NOT_FOUND);
        }
    }

    public CreatedMovieDto addMovie(AddMovieDto addMovieDto) {

        Movie savedMovie = movieRepository.save(modelMapper.map(addMovieDto, Movie.class));

        return modelMapper.map(savedMovie, CreatedMovieDto.class);
    }
}
