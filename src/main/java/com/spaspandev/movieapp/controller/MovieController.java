package com.spaspandev.movieapp.controller;

import com.spaspandev.movieapp.dto.AddMovieDto;
import com.spaspandev.movieapp.dto.CreatedMovieDto;
import com.spaspandev.movieapp.dto.DayTrendingMoviesDto;
import com.spaspandev.movieapp.service.MovieService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/movie")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/day-trending")
    public ResponseEntity<DayTrendingMoviesDto> getDayTrendingMovies() {

        return ResponseEntity.ok(movieService.getDayTrendingMovies());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMovie(@PathVariable Long id) {

        return movieService.deleteMovie(id);
    }

    @PostMapping
    public ResponseEntity<CreatedMovieDto> addMovie(@RequestBody AddMovieDto addMovieDto) {

        return ResponseEntity.ok(movieService.addMovie(addMovieDto));
    }
}
