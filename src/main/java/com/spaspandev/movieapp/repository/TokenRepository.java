package com.spaspandev.movieapp.repository;

import com.spaspandev.movieapp.model.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {


    @Query("SELECT t from Token AS t INNER JOIN User AS u ON t.user.id = u.id " +
            "WHERE u.id = ?1 AND (t.expired = false OR t.revoked = false )")
    List<Token> findAllValidTokensByUser(Long userId);

    Optional<Token> findByToken(String token);
}
