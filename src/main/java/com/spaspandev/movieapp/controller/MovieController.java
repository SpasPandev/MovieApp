package com.spaspandev.movieapp.controller;

import com.spaspandev.movieapp.dto.*;
import com.spaspandev.movieapp.service.MovieService;
import jakarta.validation.Valid;
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

    @PatchMapping("/{id}")
    public ResponseEntity<?> editMovie(@PathVariable Long id, @Valid @RequestBody EditMovieDto editMovieDto) {

        return movieService.editMovie(id, editMovieDto);
    }

    @GetMapping("/{movieName}")
    public ResponseEntity<ListOfFindedMoviesDto> findMovieByName(@PathVariable String movieName) {

        return ResponseEntity.ok(movieService.findMovieByName(movieName));
    }

    @GetMapping("/findMovieWithMostLikes")
    public ResponseEntity<?> findMovieWithMostLikes() {

        return movieService.findMovieWithMostLikes();
    }
}
