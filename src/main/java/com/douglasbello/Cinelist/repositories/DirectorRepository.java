package com.douglasbello.Cinelist.repositories;

import com.douglasbello.Cinelist.entities.Director;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DirectorRepository extends JpaRepository<Director, UUID> {
    Director findByNameContainingIgnoreCase(String name);
}