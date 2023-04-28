package com.douglasbello.Cinelist.model.repositories;

import com.douglasbello.Cinelist.model.entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
}