package com.douglasbello.Cinelist.controllers;

import com.douglasbello.Cinelist.dtos.Mapper;
import com.douglasbello.Cinelist.dtos.RequestResponseDTO;
import com.douglasbello.Cinelist.dtos.TVShowDTO;
import com.douglasbello.Cinelist.entities.TVShow;
import com.douglasbello.Cinelist.services.TVShowService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/tvshows")
public class TVShowController {
    private final TVShowService tvShowService;

    public TVShowController(TVShowService tvShowService) {
	    this.tvShowService = tvShowService;
    }

    @GetMapping
    public ResponseEntity<List<TVShowDTO>> getAll() {
        List<TVShowDTO> shows = tvShowService.findAll();
        return ResponseEntity.ok().body(shows);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        if (tvShowService.findById(id) != null) {
            TVShowDTO dto = new TVShowDTO(tvShowService.findById(id));
            return ResponseEntity.ok().body(dto);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RequestResponseDTO(404, "TVShow not found."));
    }

    @GetMapping(value = "/title/{title}")
    public ResponseEntity<?> findTvShowByTitle(@PathVariable String title) {
        if (tvShowService.findByTitle(title) != null) {
            return ResponseEntity.ok().body(tvShowService.findByTitle(title));
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RequestResponseDTO(404, "TVShow not found."));
    }

    @PostMapping
    public ResponseEntity<?> addTvShow(@RequestBody TVShowDTO tvShowDTO) {
        if (tvShowDTO == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RequestResponseDTO(400, "The show cannot be null."));
        }
        return ResponseEntity.ok().body(tvShowService.insert(Mapper.dtoToTVShow(tvShowDTO)));
    }
}