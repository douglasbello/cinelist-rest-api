package com.douglasbello.Cinelist.controllers;

import com.douglasbello.Cinelist.dtos.CommentDTO;
import com.douglasbello.Cinelist.dtos.RequestResponseDTO;
import com.douglasbello.Cinelist.entities.Comment;
import com.douglasbello.Cinelist.entities.Movie;
import com.douglasbello.Cinelist.entities.TVShow;
import com.douglasbello.Cinelist.entities.User;
import com.douglasbello.Cinelist.services.CommentService;
import com.douglasbello.Cinelist.services.MovieService;
import com.douglasbello.Cinelist.services.TVShowService;
import com.douglasbello.Cinelist.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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

    @GetMapping(value = "/movie/{movieId}")
    public ResponseEntity<?> findAllCommentsOfMovie(@PathVariable UUID movieId) {
        if (movieService.findById(movieId) == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RequestResponseDTO(HttpStatus.BAD_REQUEST.value(), "The movie doesn't exists."));
        }

        List<CommentDTO> comments = movieService.findById(movieId).getComments().stream().map(CommentDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok().body(comments);
    }

    @PostMapping(value = "/movie")
    public ResponseEntity<?> addCommentToMovie(@Valid @RequestBody CommentDTO commentDTO) {
        if (userService.findById(commentDTO.getUserId()) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RequestResponseDTO(HttpStatus.NOT_FOUND.value(), "User doesn't exists."));
        }
        if (movieService.findById(commentDTO.getShowOrMovieId()) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RequestResponseDTO(HttpStatus.NOT_FOUND.value(), "Movie doesn't exists."));
        }

        Comment comment = new Comment(userService.findById(commentDTO.getUserId()), movieService.findById(commentDTO.getShowOrMovieId()), commentDTO.getComment());
        commentDTO = new CommentDTO(commentService.insert(comment));
        return ResponseEntity.ok().body(commentDTO);
    }

    @GetMapping(value = "/show/{showId}")
    public ResponseEntity<?> findAllCommentsOfTvShow(@PathVariable UUID showId) {
        if (showId == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RequestResponseDTO(HttpStatus.BAD_REQUEST.value(), "Show id cannot be null."));
        }
        if (tvShowService.findById(showId) == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RequestResponseDTO(HttpStatus.BAD_REQUEST.value(), "The movie doesn't exists."));
        }

        List<CommentDTO> comments = tvShowService.findById(showId).getComments().stream().map(CommentDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok().body(comments);
    }

    @PostMapping(value = "/show")
    public ResponseEntity<?> addCommentToTvShow(@Valid @RequestBody CommentDTO commentDTO) {
        if (userService.findById(commentDTO.getUserId()) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RequestResponseDTO(HttpStatus.NOT_FOUND.value(), "User doesn't exists."));
        }

        if (movieService.findById(commentDTO.getShowOrMovieId()) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RequestResponseDTO(HttpStatus.NOT_FOUND.value(), "Show doesn't exists."));
        }

        Comment comment = new Comment(userService.findById(commentDTO.getUserId()), tvShowService.findById(commentDTO.getShowOrMovieId()), commentDTO.getComment());
        commentDTO = new CommentDTO(commentService.insert(comment));
        return ResponseEntity.ok().body(commentDTO);
    }

    @GetMapping(value = "/user/{userId}")
    public ResponseEntity<?> findAllCommentsOfUser(@PathVariable UUID userId) {
        if (userService.findById(userId) == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RequestResponseDTO(HttpStatus.BAD_REQUEST.value(), "The movie doesn't exists."));
        }

        List<CommentDTO> comments = userService.findById(userId).getComments().stream().map(CommentDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok().body(comments);
    }
}
