package com.douglasbello.Cinelist.controllers;

import com.douglasbello.Cinelist.dtos.MovieDTO;
import com.douglasbello.Cinelist.dtos.RequestResponseDTO;
import com.douglasbello.Cinelist.services.MovieService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping(value = "/movies")
public class MovieController {
    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping
    public ResponseEntity<Set<MovieDTO>> findAll() {
        return ResponseEntity.ok().body(movieService.findAll());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> findById(@PathVariable UUID id) {
        if (movieService.findById(id) != null) {
            return ResponseEntity.ok().body(movieService.findById(id));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RequestResponseDTO(404, "Movie with this id not found."));
    }

    @GetMapping(value = "/title={title}")
    public ResponseEntity<?> findByTitle(@PathVariable String title) {
        if (movieService.findMovieByTitle(title) != null)  {
            return ResponseEntity.ok().body(movieService.findMovieByTitle(title));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RequestResponseDTO(404, "Movie with this title not found."));
    }

    @GetMapping(value = "/director/{directorId}")
    public ResponseEntity<?> findMoviesByDirectorId(@PathVariable UUID directorId) {
        if (!movieService.findMoviesByDirectorId(directorId).isEmpty()) {
            return ResponseEntity.ok().body(movieService.findMoviesByDirectorId(directorId));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RequestResponseDTO(404, "Director doesn't exists or doesn't have any movie registered yet."));
    }
}
