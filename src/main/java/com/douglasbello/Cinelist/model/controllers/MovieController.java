package com.douglasbello.Cinelist.model.controllers;

import com.douglasbello.Cinelist.model.entities.Movie;
import com.douglasbello.Cinelist.model.services.MovieService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/movies")
public class MovieController {
    private final MovieService service;

    public MovieController(MovieService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Movie>> findAll() {
        List<Movie> movies = service.findAll();
        return ResponseEntity.ok().body(movies);
    }
}
