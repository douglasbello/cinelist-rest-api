package com.douglasbello.Cinelist.controllers;

import com.douglasbello.Cinelist.dtos.ActorDTO;
import com.douglasbello.Cinelist.dtos.RequestResponseDTO;
import com.douglasbello.Cinelist.services.ActorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping(value = "/actors")
public class ActorController {
    private final ActorService actorService;

    public ActorController(ActorService actorService) {
        this.actorService = actorService;
    }

    @GetMapping
    public ResponseEntity<Set<ActorDTO>> findAll() {
        return ResponseEntity.ok().body(actorService.findAll());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> findById(@PathVariable UUID id) {
        if (id == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RequestResponseDTO(HttpStatus.BAD_REQUEST.value(), "The id cannot be null."));
        }
        if (actorService.findById(id) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RequestResponseDTO(HttpStatus.NOT_FOUND.value(), "Actor not found."));
        }
        ActorDTO dto = new ActorDTO(actorService.findById(id));
        return ResponseEntity.ok().body(dto);
    }

    @GetMapping(value = "/name/{name}")
    public ResponseEntity<?> findByName(@PathVariable String name) {
        if (name == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RequestResponseDTO(HttpStatus.BAD_REQUEST.value(), "Name of actor cannot be null."));
        }
        if (actorService.findByName(name) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RequestResponseDTO(HttpStatus.NOT_FOUND.value(), "Actor not found."));
        }
        ActorDTO dto = actorService.findByName(name);
        return ResponseEntity.ok().body(dto);
    }

    @PostMapping
    public ResponseEntity<?> insert(@RequestBody ActorDTO actorDTO) {
        if (actorDTO == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RequestResponseDTO(HttpStatus.BAD_REQUEST.value(), "Actor cannot be null"));
        }
        if (actorDTO.getName() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RequestResponseDTO(HttpStatus.BAD_REQUEST.value(), "Actor name cannot be null"));
        }
        if (actorDTO.getBirthDate() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RequestResponseDTO(HttpStatus.BAD_REQUEST.value(), "Actor birth date cannot be null"));
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(new ActorDTO(actorService.insert(actorDTO)));
    }
}
