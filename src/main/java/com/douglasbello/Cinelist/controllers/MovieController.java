package com.douglasbello.Cinelist.controllers;

import com.douglasbello.Cinelist.dtos.MovieDTO;
import com.douglasbello.Cinelist.entities.Movie;
import com.douglasbello.Cinelist.services.MovieService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping(value = "/movies")
public class MovieController {
    private final MovieService service;

    public MovieController(MovieService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Set<MovieDTO>> getAll() {
        return ResponseEntity.ok().body(service.findAll());
    }

    @GetMapping(value = "?directorId={directorId}")
    public ResponseEntity<Set<Movie>> getMoviesOfByDirectorId(@PathVariable UUID directorId) {
        return ResponseEntity.ok().body(service.findMoviesByDirectorId(directorId));
    }
}
