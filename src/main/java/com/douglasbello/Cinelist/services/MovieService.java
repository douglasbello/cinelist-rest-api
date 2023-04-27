package com.douglasbello.Cinelist.services;

import com.douglasbello.Cinelist.model.entities.Movie;
import com.douglasbello.Cinelist.repositories.MovieRepository;
import org.springframework.stereotype.Service;

@Service
public class MovieService {
    private final MovieRepository repository;

    public MovieService(MovieRepository repository) {
        this.repository = repository;
    }

    public void save(Movie movie) {
        repository.save(movie);
    }
}