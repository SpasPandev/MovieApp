package com.spaspandev.movieapp.repository;

import com.spaspandev.movieapp.model.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    @Query("SELECT m FROM Movie AS m " +
            "WHERE m.movie_external_id = ?1")
    Optional<Movie> findByMovieExternalId(Long movie_external_id);

    @Query("SELECT m FROM Movie AS m " +
            "LEFT JOIN m.likedByUsers AS lu " +
            "GROUP BY m " +
            "ORDER BY COUNT(lu) DESC " +
            "LIMIT 1")
    Optional<Movie> findMovieWithMostLikes();
}
