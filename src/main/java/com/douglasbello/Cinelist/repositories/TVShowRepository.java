package com.douglasbello.Cinelist.repositories;

import com.douglasbello.Cinelist.entities.TVShow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TVShowRepository extends JpaRepository<TVShow, UUID> {
    List<TVShow> findByTitleContainingIgnoreCase(String title);
}
