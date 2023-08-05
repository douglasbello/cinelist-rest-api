package com.douglasbello.Cinelist.controllers;

import com.douglasbello.Cinelist.dtos.DirectorDTO;
import com.douglasbello.Cinelist.dtos.DirectorInputDTO;
import com.douglasbello.Cinelist.dtos.mapper.Mapper;
import com.douglasbello.Cinelist.dtos.RequestResponseDTO;
import com.douglasbello.Cinelist.entities.Director;
import com.douglasbello.Cinelist.services.DirectorService;
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
    public ResponseEntity<?> insert(@RequestBody DirectorInputDTO dto) {
        Director director = new Director();
        if (dto.getGender() < 1 || dto.getGender() > 3) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RequestResponseDTO(HttpStatus.BAD_REQUEST.value(), "Actor gender cannot be bigger than 1 or less than 3. The gender codes are: MALE(1), FEMALE(2), OTHER(3)"));
        }
        if (!director.setBirthDate(dto.getBirthDate())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RequestResponseDTO(HttpStatus.BAD_REQUEST.value(), "The provided date must be in the format dd/MM/yyyy."));
        }
        DirectorDTO response = new DirectorDTO(directorService.insert(Mapper.dtoToDirector(dto)));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteById(@PathVariable UUID id) {
        if (id == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RequestResponseDTO(HttpStatus.BAD_REQUEST.value(), "Id cannot be null."));
        }
        if (directorService.findById(id) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RequestResponseDTO(HttpStatus.NOT_FOUND.value(), "Director not found."));
        }
        directorService.delete(id);
        return ResponseEntity.ok().body(new RequestResponseDTO(HttpStatus.OK.value(), "Director deleted."));
    }
}
