package com.douglasbello.Cinelist.controllers;

import com.douglasbello.Cinelist.dtos.ActorDTO;
import com.douglasbello.Cinelist.dtos.RequestResponseDTO;
import com.douglasbello.Cinelist.entities.Actor;
import com.douglasbello.Cinelist.services.ActorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping( value = "/actors" )
public class ActorController {
    private final ActorService actorService;

    public ActorController(ActorService actorService) {
        this.actorService = actorService;
    }

    @GetMapping
    public ResponseEntity<Set<ActorDTO>> findAll() {
        return ResponseEntity.ok().body(actorService.findAll());
    }

    @GetMapping( value = "/{id}" )
    public ResponseEntity<?> findById(@PathVariable UUID id) {
        if ( actorService.findById(id) == null ) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RequestResponseDTO(HttpStatus.NOT_FOUND.value(), "Actor not found."));
        }
        ActorDTO dto = new ActorDTO(actorService.findById(id));
        return ResponseEntity.ok().body(dto);
    }

    @PostMapping
    public ResponseEntity<?> insert(@RequestBody ActorDTO actorDTO) {
        if ( actorDTO.getName() == null ) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RequestResponseDTO(HttpStatus.BAD_REQUEST.value(), "Actor name cannot be null"));
        }
        if ( actorDTO.getGender().getCode() < 1 || actorDTO.getGender().getCode() > 3 ) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RequestResponseDTO(HttpStatus.BAD_REQUEST.value(), "Actor gender cannot be bigger than 1 or less than 3. The gender codes are: MALE(1), FEMALE(2), OTHER(3)"));
        }
        Actor actor = new Actor();
        if ( !actor.setBirthDate(actorDTO.getBirthDate()) ) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RequestResponseDTO(HttpStatus.BAD_REQUEST.value(), "The provided birth date must be in the format dd/MM/yyyy."));
        }
        ActorDTO dto = new ActorDTO(actorService.insert(actorDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @GetMapping( value = "/name/{name}" )
    public ResponseEntity<?> findByName(@PathVariable String name) {
        if ( actorService.findByName(name) == null ) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RequestResponseDTO(HttpStatus.NOT_FOUND.value(), "Actor not found."));
        }
        Actor obj = actorService.findByName(name);
        ActorDTO dto = new ActorDTO(obj);
        return ResponseEntity.ok().body(dto);
    }
}
