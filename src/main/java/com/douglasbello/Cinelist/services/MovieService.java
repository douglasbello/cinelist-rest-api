package com.douglasbello.Cinelist.services;

import com.douglasbello.Cinelist.dtos.MovieDTO;
import com.douglasbello.Cinelist.entities.Director;
import com.douglasbello.Cinelist.entities.Movie;
import com.douglasbello.Cinelist.repositories.DirectorRepository;
import com.douglasbello.Cinelist.repositories.MovieRepository;
import com.douglasbello.Cinelist.services.exceptions.DatabaseException;
import com.douglasbello.Cinelist.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MovieService {
    private final MovieRepository repository;
    private final DirectorService directorService;

    public MovieService(MovieRepository repository, DirectorService directorService) {
        this.repository = repository;
        this.directorService = directorService;
    }

    public Set<MovieDTO> findAll() {
        return repository.findAll().stream().map(MovieDTO::new).collect(Collectors.toSet());
    }

    public Movie findById(UUID id) {
        Optional<Movie> obj = repository.findById(id);
        return obj.orElse(null);
    }

    public Set<Movie> findMoviesByDirectorId(UUID id) {
        Director director = directorService.findById(id);
        if (director != null) {
            return director.getMovies();
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
}
