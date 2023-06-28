package com.douglasbello.Cinelist.services;

import com.douglasbello.Cinelist.entities.Director;
import com.douglasbello.Cinelist.repositories.DirectorRepository;
import org.springframework.stereotype.Service;

@Service
public class DirectorService {
    private final DirectorRepository directorRepository;

    public DirectorService(DirectorRepository directorRepository) {
        this.directorRepository = directorRepository;
    }

    public Director insert(Director director) {
        Director obj = directorRepository.save(director);
        return obj;
    }
}