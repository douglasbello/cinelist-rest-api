package com.douglasbello.Cinelist.model.controllers;

import com.douglasbello.Cinelist.model.dto.GenresDTO;
import com.douglasbello.Cinelist.model.entities.Genres;
import com.douglasbello.Cinelist.model.services.GenresService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/genres")
public class GenresController {

    private final GenresService service;

    private GenresController(GenresService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<GenresDTO>> findAll() {
        List<GenresDTO> list = service.findAll();

        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenresDTO> findById(@PathVariable UUID id) {
        GenresDTO result = service.findById(id);

        return ResponseEntity.ok().body(result);
    }

    @PostMapping("/insert")
    public ResponseEntity<GenresDTO> insert(@RequestBody Genres genre) {
        GenresDTO result = service.insert(genre);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(result.getId()).toUri();
        return ResponseEntity.created(uri).body(result);
    }
}
