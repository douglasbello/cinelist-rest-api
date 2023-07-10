package com.douglasbello.Cinelist.services;

import com.douglasbello.Cinelist.dtos.ActorDTO;
import com.douglasbello.Cinelist.dtos.MovieDTO;
import com.douglasbello.Cinelist.dtos.MovieDTOResponse;
import com.douglasbello.Cinelist.entities.Actor;
import com.douglasbello.Cinelist.entities.Director;
import com.douglasbello.Cinelist.entities.Movie;
import com.douglasbello.Cinelist.repositories.MovieRepository;
import com.douglasbello.Cinelist.services.exceptions.DatabaseException;
import com.douglasbello.Cinelist.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MovieService {
    private final MovieRepository repository;
    private final DirectorService directorService;
    private final GenresService genresService;
    private final ActorService actorService;

    public MovieService(MovieRepository repository, DirectorService directorService, GenresService genresService, ActorService actorService) {
        this.repository = repository;
        this.directorService = directorService;
        this.genresService = genresService;
        this.actorService = actorService;
    }

    public Set<MovieDTOResponse> findAll() {
        return repository.findAll().stream().map(MovieDTOResponse::new).collect(Collectors.toSet());
    }

    public Movie findById(UUID id) {
        Optional<Movie> obj = repository.findById(id);
        return obj.orElse(null);
    }

    public Set<MovieDTOResponse> findMoviesByDirectorId(UUID id) {
        if (directorService.findById(id) == null) {
            return Collections.emptySet();
        }
        Director director = directorService.findById(id);
        return director.getMovies().stream().map(MovieDTOResponse::new).collect(Collectors.toSet());
    }

    public Set<MovieDTOResponse> findMoviesByDirectorName(String name) {
        if (directorService.findByName(name) == null) {
            return Collections.emptySet();
        }
        Director director = directorService.findByName(name);
        return director.getMovies().stream().map(MovieDTOResponse::new).collect(Collectors.toSet());
    }

    public MovieDTOResponse findMovieByTitle(String title) {
        title = title.replace("-", " ");
        if (repository.findMovieByTitleContainingIgnoreCase(title) != null) {
            return new MovieDTOResponse(repository.findMovieByTitleContainingIgnoreCase(title));
        }
        return null;
    }

    public Set<ActorDTO> getMovieActors(Movie movie) {
        Set<ActorDTO> response = new HashSet<>();
        if (movie.getActors().size() == 0) {
            return Collections.emptySet();
        }
        for (Actor actor : movie.getActors()) {
            response.add(new ActorDTO(actor));
        }
        return response;
    }

    public Movie addActorsToMovie(Movie movie, Set<UUID> actorsIds) {
        if (actorsIds.stream().map(actorService::findById).collect(Collectors.toSet()).size() == 0) {
            return null;
        }
        for (UUID showId : actorsIds) {
            movie.getActors().add(actorService.findById(showId));
        }
        return movie;
    }

    public Movie insert(Movie movie) {
        return repository.save(movie);
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

    public Movie update(UUID id,Movie obj) {
        try {
            Movie entity = repository.getReferenceById(id);
            updateData(entity,obj);
            return repository.save(entity);
        } catch (EntityNotFoundException exception) {
            throw new ResourceNotFoundException(id);
        }
    }

    private void updateData(Movie entity, Movie obj) {
        entity.setTitle(obj.getTitle());
        entity.setOverview(obj.getOverview());
    }

    public MovieDTO getRelatedEntities(MovieDTO movieDTO) {
        // this if is verifying if the directorIds, genresIds and actorsIds will return empty collections, if all three return empty collections the method just return the dto
        if (movieDTO.getDirectorsIds().stream().map(directorService::findById).collect(Collectors.toSet()).size() == 0 &&
            movieDTO.getGenresIds().stream().map(genresService::findById).toList().size() == 0 &&
            movieDTO.getActors().stream().map(a -> actorService.findById(a.getId())).collect(Collectors.toSet()).size() == 0) {
            return movieDTO;
        }
        for (UUID directorIds : movieDTO.getDirectorsIds()) {
            if (directorService.findById(directorIds) != null) {
                movieDTO.getDirectors().add(directorService.findById(directorIds));
            }
        }
        for (UUID genresIds : movieDTO.getGenresIds()) {
            if (genresService.findById(genresIds) != null) {
                movieDTO.getGenres().add(genresService.findById(genresIds));
            }
        }
        return movieDTO;
    }
}
