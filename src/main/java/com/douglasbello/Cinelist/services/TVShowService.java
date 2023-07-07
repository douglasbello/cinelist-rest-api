package com.douglasbello.Cinelist.services;

import com.douglasbello.Cinelist.dtos.TVShowDTO;
import com.douglasbello.Cinelist.dtos.TVShowDTOResponse;
import com.douglasbello.Cinelist.entities.*;
import com.douglasbello.Cinelist.repositories.TVShowRepository;
import com.douglasbello.Cinelist.services.exceptions.DatabaseException;
import com.douglasbello.Cinelist.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TVShowService {
    private final TVShowRepository repository;
    private final DirectorService directorService;
    private final GenresService genresService;
    private final ActorService actorService;

    public TVShowService(TVShowRepository repository, DirectorService directorService, GenresService genresService, ActorService actorService) {
        this.repository = repository;
        this.directorService = directorService;
        this.genresService = genresService;
        this.actorService = actorService;
    }

    public List<TVShowDTOResponse> findAll() {
        List<TVShowDTOResponse> shows = repository.findAll().stream().map(TVShowDTOResponse::new).collect(Collectors.toList());
        return shows;
    }

    public TVShow findById(UUID id) {
        Optional<TVShow> tvShow = repository.findById(id);
        return tvShow.orElse(null);
    }

    public Set<TVShowDTOResponse> findByTitle(String title) {
        title = title.replace("-", " ");
        if (repository.findByTitleContainingIgnoreCase(title)  != null) {
            Set<TVShowDTOResponse> dto = repository.findByTitleContainingIgnoreCase(title).stream().map(TVShowDTOResponse::new).collect(Collectors.toSet());
            return dto;
        }
        return Collections.emptySet();
    }

    public Set<TVShowDTOResponse> findTvShowsByDirectorId(UUID id) {
        if (directorService.findById(id) != null) {
            Director director = directorService.findById(id);
            return director.getTvShows().stream().map(TVShowDTOResponse::new).collect(Collectors.toSet());
        }
        return Collections.emptySet();
    }

    public Set<TVShowDTOResponse> findTvShowsByActorId(UUID id) {
        if (actorService.findById(id) != null) {
            Actor actor = actorService.findById(id);
            return actor.getTvShows().stream().map(TVShowDTOResponse::new).collect(Collectors.toSet());
        }
        return Collections.emptySet();
    }

    public TVShowDTOResponse insert(TVShow tvShow) {
        return new TVShowDTOResponse(repository.save(tvShow));
    }

    public void delete(UUID id) {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException emptyResultDataAccessException) {
            throw new ResourceNotFoundException(id);
        } catch (DataIntegrityViolationException dataIntegrityViolationException) {
            throw new DatabaseException(dataIntegrityViolationException.getMessage());
        }
    }

    public TVShowDTOResponse update(UUID id, TVShow obj) {
        try {
            TVShow entity = repository.getReferenceById(id);
            updateData(entity,obj);
            return new TVShowDTOResponse(repository.save(entity));
        } catch (EntityNotFoundException exception) {
            throw new ResourceNotFoundException(id);
        }
    }

    private void updateData(TVShow entity, TVShow obj) {
        entity.setTitle(obj.getTitle());
        entity.setOverview(obj.getOverview());
    }

    public TVShowDTO getDirectorsAndGenresAndActors(TVShowDTO tvShowDTO) {
        // this if is verifying if the directorIds, genresIds and actorsIds will return empty collections, if so, the method just return the dto
        if (tvShowDTO.getDirectorsIds().stream().map(directorService::findById).collect(Collectors.toSet()).size() == 0 &&
                tvShowDTO.getGenresIds().stream().map(genresService::findById).collect(Collectors.toList()).size() == 0 &&
                tvShowDTO.getActorsIds().stream().map(actorService::findById).collect(Collectors.toSet()).size() == 0) {
            return tvShowDTO;
        }
        for (UUID directorIds : tvShowDTO.getDirectorsIds()) {
            if (directorService.findById(directorIds) != null) {
                tvShowDTO.getDirectors().add(directorService.findById(directorIds));
            }
        }
        for (UUID genresIds : tvShowDTO.getGenresIds()) {
            if (genresService.findById(genresIds) != null) {
                tvShowDTO.getGenres().add(genresService.findById(genresIds));
            }
        }
        for (UUID actorsIds : tvShowDTO.getActorsIds()) {
            if (actorService.findById(actorsIds) != null) {
                tvShowDTO.getActors().add(actorService.findById(actorsIds));
            }
        }
        return tvShowDTO;
    }
}
