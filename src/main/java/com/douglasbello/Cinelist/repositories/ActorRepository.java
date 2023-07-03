package com.douglasbello.Cinelist.repositories;

import com.douglasbello.Cinelist.entities.Actor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ActorRepository extends JpaRepository<Actor, UUID> {
}