package com.douglasbello.Cinelist.services;

import com.douglasbello.Cinelist.dto.GenresDTO;
import com.douglasbello.Cinelist.entities.Genres;
import com.douglasbello.Cinelist.repositories.GenresRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class GenresService {

    private final GenresRepository repository;

    public GenresService(GenresRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<GenresDTO> findAll() {
        List<Genres> result = repository.findAll();
        return result.stream().map(GenresDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public GenresDTO findById(UUID id) {
        Genres result = repository.findById(id).get();
        return new GenresDTO(result);
    }

    public GenresDTO insert(Genres genre) {
        Genres obj = repository.save(genre);
        return new GenresDTO(genre);
    }

    public void insertAll(List<Genres> genresList) {
        repository.saveAll(genresList);
    }
}
