package com.douglasbello.Cinelist.services;

import com.douglasbello.Cinelist.model.entities.Movie;
import com.douglasbello.Cinelist.repositories.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {
    private final MovieRepository repository;

    public MovieService(MovieRepository repository) {
        this.repository = repository;
    }

    public List<Movie> findAll() {
        return repository.findAll();
    }

    public void save(Movie movie) {
        repository.save(movie);
    }
}