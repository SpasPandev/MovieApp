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
}
