package com.douglasbello.Cinelist.services;

import com.douglasbello.Cinelist.dtos.actor.ActorDTO;
import com.douglasbello.Cinelist.dtos.movie.MovieDTOResponse;
import com.douglasbello.Cinelist.dtos.show.TVShowDTOResponse;
import com.douglasbello.Cinelist.entities.Actor;
import com.douglasbello.Cinelist.repositories.ActorRepository;
import com.douglasbello.Cinelist.services.exceptions.ResourceNotFoundException;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ActorService {
    private final ActorRepository actorRepository;

    public ActorService(ActorRepository actorRepository) {
        this.actorRepository = actorRepository;
    }

    public Actor insert(Actor actor) {
        return actorRepository.save(actor);
    }

    public List<Actor> insertAll(List<Actor> actors) {
        return actorRepository.saveAll(actors);
    }

    public Set<ActorDTO> findAll() {
        return actorRepository.findAll().stream().map(ActorDTO::new).collect(Collectors.toSet());
    }

    public Actor findById(UUID id) {
        Optional<Actor> actor = actorRepository.findById(id);
        return actor.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public Actor findByName(String name) {
        name = name.replace("-", " ");
        if ( actorRepository.findByNameContainingIgnoreCase(name) != null ) {
            return actorRepository.findByNameContainingIgnoreCase(name);
        }
        return null;
    }

    public Set<MovieDTOResponse> findMoviesByActorId(UUID id) {
        if ( findById(id) == null ) {
            return Collections.emptySet();
        }
        Actor actor = findById(id);
        return actor.getMovies().stream().map(MovieDTOResponse::new).collect(Collectors.toSet());
    }

    public Set<MovieDTOResponse> findMoviesByActorName(String name) {
        if ( findByName(name) == null ) {
            return Collections.emptySet();
        }
        Actor actor = findByName(name);
        return actor.getMovies().stream().map(MovieDTOResponse::new).collect(Collectors.toSet());
    }

    public Set<TVShowDTOResponse> findShowsByActorId(UUID id) {
        if ( findById(id) == null ) {
            return Collections.emptySet();
        }
        Actor actor = findById(id);
        return actor.getTvShows().stream().map(TVShowDTOResponse::new).collect(Collectors.toSet());
    }

    public Set<TVShowDTOResponse> findShowsByActorName(String name) {
        if ( findByName(name) == null ) {
            return Collections.emptySet();
        }
        Actor actor = findByName(name);
        return actor.getTvShows().stream().map(TVShowDTOResponse::new).collect(Collectors.toSet());
    }
}
