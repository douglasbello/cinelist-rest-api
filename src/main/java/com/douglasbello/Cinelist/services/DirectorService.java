package com.douglasbello.Cinelist.services;

import com.douglasbello.Cinelist.entities.Director;
import com.douglasbello.Cinelist.entities.User;
import com.douglasbello.Cinelist.repositories.DirectorRepository;
import com.douglasbello.Cinelist.services.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DirectorService {
    private final DirectorRepository directorRepository;

    public DirectorService(DirectorRepository directorRepository) {
        this.directorRepository = directorRepository;
    }

    public List<Director> getAll() {
        return directorRepository.findAll();
    }

    public Director findById(UUID id) {
        Optional<Director> user = directorRepository.findById(id);
        return user.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public Director insert(Director director) {
        Director obj = directorRepository.save(director);
        return obj;
    }
}