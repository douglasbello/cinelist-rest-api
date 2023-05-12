package com.douglasbello.Cinelist.model.repositories;

import com.douglasbello.Cinelist.model.entities.Genres;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GenresRepository extends JpaRepository<Genres, UUID> {
}