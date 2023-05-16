package com.douglasbello.Cinelist.model.controllers;

import com.douglasbello.Cinelist.model.entities.TVShow;
import com.douglasbello.Cinelist.model.services.TVShowService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/tvshows")
public class TVShowController {
    private final TVShowService service;

    public TVShowController(TVShowService service) {
	this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<TVShow>> findAll() {
        return ResponseEntity.ok().body(service.findAll());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<TVShow> findById(@PathVariable UUID id) {
        return ResponseEntity.ok().body(service.findById(id));
    }

    @PostMapping(value = "/add")
    public ResponseEntity<TVShow> insert(@RequestBody TVShow obj) {
        obj = service.insert(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).body(obj);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<TVShow> update(@PathVariable UUID id, @RequestBody TVShow obj) {
        obj = service.update(id,obj);
        return ResponseEntity.ok().body(obj);
    }
}
