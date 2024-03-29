package com.douglasbello.Cinelist.repositories;

import com.douglasbello.Cinelist.entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.UUID;

@Repository
public interface MovieRepository extends JpaRepository<Movie, UUID> {
    Set<Movie> findMovieByTitleContainingIgnoreCase(String title);
    long count();
}
