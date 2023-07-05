package com.douglasbello.Cinelist.services;

import com.douglasbello.Cinelist.dtos.MovieDTO;
import com.douglasbello.Cinelist.dtos.MovieDTOResponse;
import com.douglasbello.Cinelist.dtos.RequestResponseDTO;
import com.douglasbello.Cinelist.entities.Director;
import com.douglasbello.Cinelist.entities.Movie;
import com.douglasbello.Cinelist.repositories.MovieRepository;
import com.douglasbello.Cinelist.services.exceptions.DatabaseException;
import com.douglasbello.Cinelist.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MovieService {
    private final MovieRepository repository;
    private final DirectorService directorService;
    private final GenresService genresService;

    public MovieService(MovieRepository repository, DirectorService directorService, GenresService genresService) {
        this.repository = repository;
        this.directorService = directorService;
        this.genresService = genresService;
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

    public MovieDTO getDirectorsAndGenres(MovieDTO movieDTO) {
        // this if is verifying if both the directorIds and genresIds will return empty collections, if so, the method just return the dto
        if (movieDTO.getDirectorsIds().stream().map(directorService::findById).collect(Collectors.toSet()).size() == 0 ||
                movieDTO.getGenresIds().stream().map(genresService::findById).collect(Collectors.toList()).size() == 0) {
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
