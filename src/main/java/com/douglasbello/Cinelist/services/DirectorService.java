package com.douglasbello.Cinelist.services;

import com.douglasbello.Cinelist.dtos.DirectorDTO;
import com.douglasbello.Cinelist.dtos.MovieDTOResponse;
import com.douglasbello.Cinelist.dtos.TVShowDTOResponse;
import com.douglasbello.Cinelist.entities.Director;
import com.douglasbello.Cinelist.entities.User;
import com.douglasbello.Cinelist.repositories.DirectorRepository;
import com.douglasbello.Cinelist.services.exceptions.DatabaseException;
import com.douglasbello.Cinelist.services.exceptions.ResourceNotFoundException;
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
        return user.orElse(null);
    }

    public Director findByName(String name) {
        name = name.replace("-", " ");
        if (directorRepository.findByNameContainingIgnoreCase(name) == null) {
            return null;
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
            return Collections.emptySet();
        }
        Director director = findById(id);
        return director.getMovies().stream().map(MovieDTOResponse::new).collect(Collectors.toSet());
    }

    public Set<MovieDTOResponse> findMoviesByDirectorName(String name) {
        if (findByName(name) == null) {
            return Collections.emptySet();
        }
        Director director = findByName(name);
        return director.getMovies().stream().map(MovieDTOResponse::new).collect(Collectors.toSet());
    }

    public Set<TVShowDTOResponse> findShowsByDirectorId(UUID id) {
        if (findById(id) == null) {
            return Collections.emptySet();
        }
        Director director = findById(id);
        return director.getTvShows().stream().map(TVShowDTOResponse::new).collect(Collectors.toSet());
    }

    public Set<TVShowDTOResponse> findShowsByDirectorName(String name) {
        if (findByName(name) == null) {
            return Collections.emptySet();
        }
        Director director = findByName(name);
        return director.getTvShows().stream().map(TVShowDTOResponse::new).collect(Collectors.toSet());
    }
}