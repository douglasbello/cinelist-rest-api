package com.douglasbello.Cinelist.controllers;

import com.douglasbello.Cinelist.dtos.RequestResponseDTO;
import com.douglasbello.Cinelist.entities.Genres;
import com.douglasbello.Cinelist.services.GenresService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "/genres")
public class GenresController {

    private final GenresService service;

    private GenresController(GenresService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok().body(service.findAll());
    }

    @GetMapping(value = "/id/{id}")
    public ResponseEntity<?> findById(@PathVariable UUID id) {
        if (service.findById(id) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RequestResponseDTO(HttpStatus.NOT_FOUND.value(), "Genre not found."));
        }
        return ResponseEntity.ok().body(service.findById(id));
    }

    @GetMapping(value = "/name/{name}")
    public ResponseEntity<?> findByName(@PathVariable String name) {
        if (service.findByGenre(name) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RequestResponseDTO(HttpStatus.NOT_FOUND.value(), "Genre not found."));
        }
        return ResponseEntity.ok().body(service.findByGenre(name));
    }

    @PostMapping
    public ResponseEntity<?> insert(@RequestBody String genreName) {
        if (genreName.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RequestResponseDTO(HttpStatus.BAD_REQUEST.value(), "Genre name cannot be empty."));
        }
        Genres genres = new Genres(genreName);
        return ResponseEntity.status(HttpStatus.CREATED).body(service.insert(genres));
    }
}
