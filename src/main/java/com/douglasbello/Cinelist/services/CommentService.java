package com.douglasbello.Cinelist.services;

import com.douglasbello.Cinelist.model.entities.Comment;
import com.douglasbello.Cinelist.repositories.CommentRepository;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    private final CommentRepository repository;

    public CommentService(CommentRepository repository) {
        this.repository = repository;
    }

    public void save(Comment comment) {
        repository.save(comment);
    }
}