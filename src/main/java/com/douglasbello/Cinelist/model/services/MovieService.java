package com.douglasbello.Cinelist.model.services;

import com.douglasbello.Cinelist.model.entities.Movie;
import com.douglasbello.Cinelist.model.repositories.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MovieService {
    private final MovieRepository repository;

    public MovieService(MovieRepository repository) {
        this.repository = repository;
    }

    public List<Movie> findAll() {
        return repository.findAll();
    }

    public Movie findById(UUID id) {
        Optional<Movie> obj = repository.findById(id);
        return obj.orElse(null);
    }

    public Movie insert(Movie movie) {
        return repository.save(movie);
    }

    public void delete(UUID id) {
        repository.deleteById(id);
    }

    public Movie update(UUID id, Movie obj) {
        Movie entity = repository.getReferenceById(id);
        updateData(entity,obj);
        return repository.save(entity);
    }

    private void updateData(Movie entity, Movie obj) {
        entity.setTitle(obj.getTitle());
        entity.setOverview(obj.getOverview());
    }
}