package com.douglasbello.Cinelist.repositories;

import com.douglasbello.Cinelist.entities.Genres;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface GenresRepository extends JpaRepository<Genres, UUID> {
    Genres findByGenreContainingIgnoreCase(String name);
}
