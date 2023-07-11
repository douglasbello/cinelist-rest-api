package com.douglasbello.Cinelist.services;

import com.douglasbello.Cinelist.dtos.ActorDTO;
import com.douglasbello.Cinelist.dtos.mapper.Mapper;
import com.douglasbello.Cinelist.entities.Actor;
import com.douglasbello.Cinelist.repositories.ActorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public Actor insert(ActorDTO dto) {
        return actorRepository.save(Mapper.dtoToActor(dto));
    }

    public List<Actor> insertAll(List<Actor> actors) {
        return actorRepository.saveAll(actors);
    }

    public Set<ActorDTO> findAll() {
        return actorRepository.findAll().stream().map(ActorDTO::new).collect(Collectors.toSet());
    }

    public Actor findById(UUID id) {
        Optional<Actor> actor = actorRepository.findById(id);
        return actor.orElse(null);
    }

    public Actor findByName(String name) {
        name = name.replace("-", " ");
        if (actorRepository.findByNameContainingIgnoreCase(name) != null) {
            return actorRepository.findByNameContainingIgnoreCase(name);
        }
        return null;
    }
}