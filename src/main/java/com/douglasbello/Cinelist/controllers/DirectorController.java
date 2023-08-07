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
    public ResponseEntity<?> findById(@PathVariable UUID id) {
        if (directorService.findById(id) != null) {
            return ResponseEntity.ok().body(directorService.findById(id));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RequestResponseDTO(404, "TV Show not found."));
    }

    @PostMapping
    public ResponseEntity<?> insert(@Valid @RequestBody DirectorInputDTO dto) {
        if (dto.getGender() < 1 || dto.getGender() > 3) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RequestResponseDTO(HttpStatus.BAD_REQUEST.value(), "Gender code cannot be less than 1 or bigger than 3. The gender codes are: MALE(1), FEMALE(2), PREFER NOT SAY(3)"));
        }

        Director director = new Director();
        if (!director.setBirthDate(dto.getBirthDate())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RequestResponseDTO(HttpStatus.BAD_REQUEST.value(), "The provided date must be in the format dd/MM/yyyy."));
        }
        DirectorDTO response = new DirectorDTO(directorService.insert(Mapper.dtoToDirector(dto)));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteById(@PathVariable UUID id) {
        if (directorService.findById(id) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RequestResponseDTO(HttpStatus.NOT_FOUND.value(), "Director not found."));
        }
        directorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
