package com.douglasbello.Cinelist.repositories;

import com.douglasbello.Cinelist.entities.Actor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ActorRepository extends JpaRepository<Actor, UUID> {
    Actor findByNameContainingIgnoreCase(String name);
}