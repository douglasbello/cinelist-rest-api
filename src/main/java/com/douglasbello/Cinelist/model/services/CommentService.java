package com.douglasbello.Cinelist.model.services;

import com.douglasbello.Cinelist.model.entities.Comment;
import com.douglasbello.Cinelist.model.repositories.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CommentService {
    private final CommentRepository repository;

    public CommentService(CommentRepository repository) {
        this.repository = repository;
    }

    public List<Comment> findAll() {
        return repository.findAll();
    }

    public Comment findById(UUID id) {
        Optional<Comment> obj = repository.findById(id);
        return obj.orElseThrow(RuntimeException::new);
    }

    public Comment insert(Comment comment) {
        return repository.save(comment);
    }

    public void delete(UUID id) {
        repository.deleteById(id);
    }
}