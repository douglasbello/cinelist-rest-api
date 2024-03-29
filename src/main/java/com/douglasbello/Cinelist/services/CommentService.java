package com.douglasbello.Cinelist.services;

import com.douglasbello.Cinelist.entities.Comment;
import com.douglasbello.Cinelist.repositories.CommentRepository;
import com.douglasbello.Cinelist.services.exceptions.ResourceNotFoundException;
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
        return obj.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public Comment insert(Comment comment) {
        return repository.save(comment);
    }

    public void delete(UUID id) {
        repository.deleteById(id);
    }

    public List<Comment> findAllCommentsByMovie(UUID id) {
        return repository.findAllCommentsByMovieId(id);
    }

    public List<Comment> findAllCommentsByTvShow(UUID id) {
        return repository.findAllCommentsByTvShowId(id);
    }
}
