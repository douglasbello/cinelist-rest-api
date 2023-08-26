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
        if (dto.getGender() < 1 || dto.getGender() > 3) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RequestResponseDTO(HttpStatus.BAD_REQUEST.value(), "Gender code cannot be less than 1 or bigger than 3. The gender codes are: MALE(1), FEMALE(2), PREFER NOT SAY(3)"));
        }
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
        if (actorService.findByName(name) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RequestResponseDTO(HttpStatus.NOT_FOUND.value(), "Actor not found."));
        }
        Actor obj = actorService.findByName(name);
        ActorDTO dto = new ActorDTO(obj);
        return ResponseEntity.ok().body(dto);
    }
}
