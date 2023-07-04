package com.douglasbello.Cinelist.services;

import com.douglasbello.Cinelist.dtos.ActorDTO;
import com.douglasbello.Cinelist.dtos.Mapper;
import com.douglasbello.Cinelist.entities.Actor;
import com.douglasbello.Cinelist.entities.User;
import com.douglasbello.Cinelist.repositories.ActorRepository;
import com.douglasbello.Cinelist.services.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ActorService {
    private final ActorRepository actorRepository;

    public ActorService(ActorRepository actorRepository) {
        this.actorRepository = actorRepository;
    }

    public ActorDTO insert(ActorDTO dto) {
        return new ActorDTO(actorRepository.save(Mapper.dtoToActor(dto)));
    }

    public Set<ActorDTO> findAll() {
        return actorRepository.findAll().stream().map(ActorDTO::new).collect(Collectors.toSet());
    }

    public Actor findById(UUID id) {
        Optional<Actor> actor = actorRepository.findById(id);
        return actor.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public ActorDTO findByName(String name) {
        name = name.replace("-", " ");
        if (actorRepository.findByNameContainingIgnoreCase(name) != null) {
            return new ActorDTO(actorRepository.findByNameContainingIgnoreCase(name));
        }
        return null;
    }
}