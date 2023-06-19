package com.douglasbello.Cinelist.repositories;

import com.douglasbello.Cinelist.entities.Genres;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GenresRepository extends JpaRepository<Genres, UUID> {
}
