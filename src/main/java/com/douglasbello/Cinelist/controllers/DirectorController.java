package com.douglasbello.Cinelist.controllers;

import com.douglasbello.Cinelist.dtos.director.DirectorDTO;
import com.douglasbello.Cinelist.dtos.director.DirectorInputDTO;
import com.douglasbello.Cinelist.dtos.Mapper;
import com.douglasbello.Cinelist.dtos.RequestResponseDTO;
import com.douglasbello.Cinelist.entities.Director;
import com.douglasbello.Cinelist.services.DirectorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping(value = "/directors")
public class DirectorController {
    private final DirectorService directorService;

    public DirectorController(DirectorService directorService) {
        this.directorService = directorService;
    }

    @GetMapping
    public ResponseEntity<Set<DirectorDTO>> findAll() {
        return ResponseEntity.ok().body(directorService.findAll());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<DirectorDTO> findById(@PathVariable UUID id) {
        Director director = directorService.findById(id);
        DirectorDTO dto = new DirectorDTO(director);
        return ResponseEntity.ok().body(dto);
    }

    @PostMapping
    public ResponseEntity<?> insert(@Valid @RequestBody DirectorInputDTO dto) {
        Director director = new Director();
        if (!director.setBirthDate(dto.getBirthDate())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RequestResponseDTO(HttpStatus.BAD_REQUEST.value(), "The provided date must be in the format dd/MM/yyyy."));
        }
        DirectorDTO response = new DirectorDTO(directorService.insert(Mapper.dtoToDirector(dto)));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteById(@PathVariable UUID id) {
        directorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
