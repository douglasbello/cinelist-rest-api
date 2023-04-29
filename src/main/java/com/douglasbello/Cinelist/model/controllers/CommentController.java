package com.douglasbello.Cinelist.model.controllers;


import com.douglasbello.Cinelist.model.entities.Comment;
import com.douglasbello.Cinelist.model.entities.Movie;
import com.douglasbello.Cinelist.model.entities.TVShow;
import com.douglasbello.Cinelist.model.entities.User;
import com.douglasbello.Cinelist.model.services.CommentService;
import com.douglasbello.Cinelist.model.services.MovieService;
import com.douglasbello.Cinelist.model.services.TVShowService;
import com.douglasbello.Cinelist.model.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/comments")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

    @Autowired
    private MovieService movieService;

    @Autowired
    private TVShowService tvShowService;



    @GetMapping
    public ResponseEntity<List<Comment>> findAll() {
        List<Comment> comments = commentService.findAll();
        return ResponseEntity.ok().body(comments);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Comment> findById(@PathVariable UUID id) {
        return ResponseEntity.ok().body(commentService.findById(id));
    }

    @PostMapping(value = "/comment")
    public ResponseEntity<Comment> insert(@RequestBody UUID userId, UUID showOrMovieId, String comment) {
        Comment obj;
        User user = userService.findById(userId);
        Movie movie = movieService.findById(showOrMovieId);
        TVShow tvShow;
        if (movie == null) {
            tvShow = tvShowService.findById(showOrMovieId);
            obj = new Comment(user,tvShow,comment);
            obj = commentService.insert(obj);
        } else {
            obj = new Comment(user,movie,comment);
            obj = commentService.insert(obj);
        }
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).body(obj);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        commentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}