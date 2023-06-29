package com.douglasbello.Cinelist.services;

import com.douglasbello.Cinelist.entities.Director;
import com.douglasbello.Cinelist.repositories.DirectorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DirectorService {
    private final DirectorRepository directorRepository;

    public DirectorService(DirectorRepository directorRepository) {
        this.directorRepository = directorRepository;
    }

    public List<Director> getAll() {
        return directorRepository.findAll();
    }

    public Director insert(Director director) {
        Director obj = directorRepository.save(director);
        return obj;
    }
}