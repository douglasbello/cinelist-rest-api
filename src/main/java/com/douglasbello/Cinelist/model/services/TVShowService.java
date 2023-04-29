package com.douglasbello.Cinelist.model.services;

import com.douglasbello.Cinelist.model.entities.TVShow;
import com.douglasbello.Cinelist.model.repositories.TVShowRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TVShowService {
    private final TVShowRepository repository;

    public TVShowService(TVShowRepository repository) {
        this.repository = repository;
    }

    public List<TVShow> findAll() {
        return repository.findAll();
    }

    public TVShow findById(UUID id) {
        Optional<TVShow> tvShow = repository.findById(id);
        return tvShow.orElse(null);
    }

    public TVShow insert(TVShow tvShow) {
        return repository.save(tvShow);
    }

    public void delete(UUID id) {
        repository.deleteById(id);
    }

    public TVShow update(UUID id, TVShow obj) {
        TVShow entity = repository.getReferenceById(id);
        updateData(entity,obj);
        return repository.save(entity);
    }

    private void updateData(TVShow entity, TVShow obj) {
        entity.setTitle(obj.getTitle());
        entity.setOverview(obj.getOverview());
    }
}