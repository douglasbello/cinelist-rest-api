package com.douglasbello.Cinelist.controllers;

import java.util.Set;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.douglasbello.Cinelist.dtos.Mapper;
import com.douglasbello.Cinelist.dtos.RequestResponseDTO;
import com.douglasbello.Cinelist.dtos.actor.ActorDTO;
import com.douglasbello.Cinelist.dtos.actor.ActorInputDTO;
import com.douglasbello.Cinelist.entities.Actor;
import com.douglasbello.Cinelist.services.ActorService;

import jakarta.validation.Valid;

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
        ActorDTO dto = new ActorDTO(actorService.findById(id));
        return ResponseEntity.ok().body(dto);
    }

    @PostMapping
    public ResponseEntity<?> insert(@Valid @RequestBody ActorInputDTO dto) {
        Actor actor = new Actor();
        if (!actor.setBirthDate(dto.getBirthDate())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RequestResponseDTO(HttpStatus.BAD_REQUEST.value(), "The provided birth date must be in the format dd/MM/yyyy."));
        }

        actor = Mapper.dtoToActor(dto);
        ActorDTO response = new ActorDTO(actorService.insert(actor));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping(value = "/name/{name}")
    public ResponseEntity<?> findByName(@PathVariable String name) {
        Actor obj = actorService.findByName(name);
        ActorDTO dto = new ActorDTO(obj);
        return ResponseEntity.ok().body(dto);
    }
}
