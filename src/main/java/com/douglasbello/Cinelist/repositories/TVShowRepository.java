package com.douglasbello.Cinelist.repositories;

import com.douglasbello.Cinelist.entities.TVShow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TVShowRepository extends JpaRepository<TVShow, UUID> {
    TVShow findByTitleContainingIgnoreCase(String title);
}
