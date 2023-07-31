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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping( value = "/comments" )
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

    @GetMapping( value = "/movie/{movieId}" )
    public ResponseEntity<?> findAllCommentsOfMovie(@PathVariable UUID movieId) {
        if ( movieService.findById(movieId) == null ) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RequestResponseDTO(HttpStatus.BAD_REQUEST.value(), "The movie doesn't exists."));
        }

        List<CommentDTO> comments = movieService.findById(movieId).getComments().stream().map(CommentDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok().body(comments);
    }

    @PostMapping( value = "/movie" )
    public ResponseEntity<?> addCommentToMovie(@RequestBody CommentDTO commentDTO) {
        if ( commentDTO.getUserId() == null ) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RequestResponseDTO(HttpStatus.BAD_REQUEST.value(), "User id cannot be null."));
        }
        if ( userService.findById(commentDTO.getUserId()) == null ) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RequestResponseDTO(HttpStatus.NOT_FOUND.value(), "User doesn't exists."));
        }
        if ( commentDTO.getShowOrMovieId() == null ) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RequestResponseDTO(HttpStatus.BAD_REQUEST.value(), "Movie id cannot be null."));
        }
        if ( movieService.findById(commentDTO.getShowOrMovieId()) == null ) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RequestResponseDTO(HttpStatus.NOT_FOUND.value(), "Movie doesn't exists."));
        }

        User user = userService.findById(commentDTO.getUserId());
        Movie movie = movieService.findById(commentDTO.getShowOrMovieId());
        Comment comment = new Comment(user, movie, commentDTO.getComment());
        comment = commentService.insert(comment);
        commentDTO = new CommentDTO(comment);
        return ResponseEntity.ok().body(commentDTO);
    }

    @GetMapping( value = "/tvshow/{tvshowId}" )
    public ResponseEntity<?> findAllCommentsOfTvShow(@PathVariable UUID tvshowId) {
        if ( tvshowId == null ) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RequestResponseDTO(HttpStatus.BAD_REQUEST.value(), "Show id cannot be null."));
        }
        if ( tvShowService.findById(tvshowId) == null ) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RequestResponseDTO(HttpStatus.BAD_REQUEST.value(), "The movie doesn't exists."));
        }

        List<CommentDTO> comments = tvShowService.findById(tvshowId).getComments().stream().map(CommentDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok().body(comments);
    }

    @PostMapping( value = "/tvshow" )
    public ResponseEntity<?> addCommentToTvShow(@RequestBody CommentDTO commentDTO) {
        if ( commentDTO.getUserId() == null ) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RequestResponseDTO(HttpStatus.BAD_REQUEST.value(), "User id cannot be null."));
        }
        if ( userService.findById(commentDTO.getUserId()) == null ) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RequestResponseDTO(HttpStatus.NOT_FOUND.value(), "User doesn't exists."));
        }
        if ( commentDTO.getShowOrMovieId() == null ) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RequestResponseDTO(HttpStatus.BAD_REQUEST.value(), "Show id cannot be null."));
        }
        if ( movieService.findById(commentDTO.getShowOrMovieId()) == null ) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RequestResponseDTO(HttpStatus.NOT_FOUND.value(), "Show doesn't exists."));
        }

        User user = userService.findById(commentDTO.getUserId());
        TVShow tvShow = tvShowService.findById(commentDTO.getShowOrMovieId());
        Comment comment = new Comment(user, tvShow, commentDTO.getComment());
        comment = commentService.insert(comment);
        commentDTO = new CommentDTO(comment);
        return ResponseEntity.ok().body(commentDTO);
    }

    @GetMapping( value = "/user/{userId}" )
    public ResponseEntity<?> findAllCommentsOfUser(@PathVariable UUID userId) {
        if ( userService.findById(userId) == null ) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RequestResponseDTO(HttpStatus.BAD_REQUEST.value(), "The movie doesn't exists."));
        }

        List<CommentDTO> comments = userService.findById(userId).getComments().stream().map(CommentDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok().body(comments);
    }
}
