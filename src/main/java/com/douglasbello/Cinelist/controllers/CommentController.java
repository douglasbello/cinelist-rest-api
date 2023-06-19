package com.douglasbello.Cinelist.controllers;


import com.douglasbello.Cinelist.dto.CommentDTO;
import com.douglasbello.Cinelist.entities.Comment;
import com.douglasbello.Cinelist.entities.Movie;
import com.douglasbello.Cinelist.entities.TVShow;
import com.douglasbello.Cinelist.entities.User;
import com.douglasbello.Cinelist.services.CommentService;
import com.douglasbello.Cinelist.services.MovieService;
import com.douglasbello.Cinelist.services.TVShowService;
import com.douglasbello.Cinelist.services.UserService;
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
    private final CommentService commentService;

    private final UserService userService;

    private final MovieService movieService;

    private final TVShowService tvShowService;

    public CommentController(CommentService commentService, UserService userService,
	    MovieService movieService, TVShowService tvShowService) {
	this.commentService = commentService;
	this.userService = userService;
	this.movieService = movieService;
	this.tvShowService = tvShowService;
    }
    

    @GetMapping
    public ResponseEntity<List<Comment>> findAll() {
        List<Comment> comments = commentService.findAll();
        return ResponseEntity.ok().body(comments);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Comment> findById(@PathVariable UUID id) {
        return ResponseEntity.ok().body(commentService.findById(id));
    }

    @GetMapping(value = "/movie/{id}")
    public ResponseEntity<List<Comment>> findAllCommentsByMovie(@PathVariable UUID id) {
        List<Comment> comments = commentService.findAllCommentsByMovie(id);
        return ResponseEntity.ok().body(comments);
    }

    @GetMapping(value = "/tvshow/{id}")
    public ResponseEntity<List<Comment>> findAllCommentsByTvShow(@PathVariable UUID id) {
        List<Comment> comments = commentService.findAllCommentsByTvShow(id);
        return ResponseEntity.ok().body(comments);
    }


    @PostMapping(value = "/comment")
    public ResponseEntity<Comment> insert(@RequestBody CommentDTO commentDTO) {
        Comment obj;
        User user = userService.findById(commentDTO.getUserId());
        TVShow tvShow;
        Movie movie = movieService.findById(commentDTO.getShowOrMovieId());
        if (movie != null) {
            obj = new Comment(user,movie,commentDTO.getComment());
        } else {
            tvShow = tvShowService.findById(commentDTO.getShowOrMovieId());
            obj = new Comment(user,tvShow,commentDTO.getComment());
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