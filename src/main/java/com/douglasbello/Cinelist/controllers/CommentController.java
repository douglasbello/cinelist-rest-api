package com.douglasbello.Cinelist.controllers;

import com.douglasbello.Cinelist.dtos.comment.CommentDTO;
import com.douglasbello.Cinelist.dtos.RequestResponseDTO;
import com.douglasbello.Cinelist.entities.Comment;
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
        List<CommentDTO> comments = movieService.findById(movieId).getComments().stream().map(CommentDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok().body(comments);
    }

    @PostMapping(value = "/movie")
    public ResponseEntity<?> addCommentToMovie(@Valid @RequestBody CommentDTO commentDTO) {
        Comment comment = new Comment(userService.findById(commentDTO.getUserId()), movieService.findById(commentDTO.getShowOrMovieId()), commentDTO.getComment());
        commentDTO = new CommentDTO(commentService.insert(comment));
        return ResponseEntity.ok().body(commentDTO);
    }

    @GetMapping(value = "/show/{showId}")
    public ResponseEntity<?> findAllCommentsOfTvShow(@PathVariable UUID showId) {
        List<CommentDTO> comments = tvShowService.findById(showId).getComments().stream().map(CommentDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok().body(comments);
    }

    @PostMapping(value = "/show")
    public ResponseEntity<?> addCommentToTvShow(@Valid @RequestBody CommentDTO commentDTO) {
        Comment comment = new Comment(userService.findById(commentDTO.getUserId()), tvShowService.findById(commentDTO.getShowOrMovieId()), commentDTO.getComment());
        commentDTO = new CommentDTO(commentService.insert(comment));
        return ResponseEntity.ok().body(commentDTO);
    }

    @GetMapping(value = "/user/{userId}")
    public ResponseEntity<?> findAllCommentsOfUser(@PathVariable UUID userId) {
        List<CommentDTO> comments = userService.findById(userId).getComments().stream().map(CommentDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok().body(comments);
    }
}
