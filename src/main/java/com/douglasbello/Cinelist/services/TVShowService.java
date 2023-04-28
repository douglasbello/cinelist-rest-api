package com.douglasbello.Cinelist.services;

import com.douglasbello.Cinelist.model.entities.TVShow;
import com.douglasbello.Cinelist.repositories.TVShowRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TVShowService {
    private final TVShowRepository repository;

    public TVShowService(TVShowRepository repository) {
        this.repository = repository;
    }

    public void save(TVShow tvShow) {
        repository.save(tvShow);
    }

    public List<TVShow> findAll() {
        return repository.findAll();
    }
}