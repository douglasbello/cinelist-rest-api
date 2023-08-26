package com.douglasbello.Cinelist.services;

import com.douglasbello.Cinelist.dtos.director.DirectorDTO;
import com.douglasbello.Cinelist.dtos.movie.MovieDTOResponse;
import com.douglasbello.Cinelist.dtos.show.TVShowDTOResponse;
import com.douglasbello.Cinelist.entities.Director;
import com.douglasbello.Cinelist.repositories.DirectorRepository;
import com.douglasbello.Cinelist.services.exceptions.DatabaseException;
import com.douglasbello.Cinelist.services.exceptions.ResourceNotFoundException;
import com.douglasbello.Cinelist.services.exceptions.ResourceNotFoundWithNameException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class DirectorService {
    private final DirectorRepository directorRepository;

    public DirectorService(DirectorRepository directorRepository) {
        this.directorRepository = directorRepository;
    }

    public Set<DirectorDTO> findAll() {
        return directorRepository.findAll().stream().map(DirectorDTO::new).collect(Collectors.toSet());
    }

    public Director findById(UUID id) {
        Optional<Director> user = directorRepository.findById(id);
        return user.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public Director findByName(String name) {
        name = name.replace("-", " ");
        if (directorRepository.findByNameContainingIgnoreCase(name) == null) {
            throw new ResourceNotFoundWithNameException(name);
        }
        return directorRepository.findByNameContainingIgnoreCase(name);
    }

    public Director insert(Director director) {
        Director obj = directorRepository.save(director);
        return obj;
    }

    public void delete(UUID id) {
        try {
            directorRepository.deleteById(id);
        } catch (EmptyResultDataAccessException emptyResultDataAccessException) {
            throw new ResourceNotFoundException(id);
        } catch (DataIntegrityViolationException dataIntegrityViolationException) {
            throw new DatabaseException(dataIntegrityViolationException.getMessage());
        }
    }

    public Set<MovieDTOResponse> findMoviesByDirectorId(UUID id) {
        if (findById(id) == null) {
            throw new ResourceNotFoundException(id);
        }
        Director director = findById(id);
        return director.getMovies().stream().map(MovieDTOResponse::new).collect(Collectors.toSet());
    }

    public Set<MovieDTOResponse> findMoviesByDirectorName(String name) {
        if (findByName(name) != null) {
            Director director = findByName(name);
            return director.getMovies().stream().map(MovieDTOResponse::new).collect(Collectors.toSet());
        }
        throw new ResourceNotFoundWithNameException(name);
    }

    public Set<TVShowDTOResponse> findShowsByDirectorId(UUID id) {
        if (findById(id) == null) {
            throw new ResourceNotFoundException(id);
        }
        Director director = findById(id);
        return director.getTvShows().stream().map(TVShowDTOResponse::new).collect(Collectors.toSet());
    }

    public Set<TVShowDTOResponse> findShowsByDirectorName(String name) {
        if (findByName(name) == null) {
            throw new ResourceNotFoundWithNameException(name);
        }
        Director director = findByName(name);
        return director.getTvShows().stream().map(TVShowDTOResponse::new).collect(Collectors.toSet());
    }
}
