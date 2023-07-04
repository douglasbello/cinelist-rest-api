package com.douglasbello.Cinelist.controllers;

import com.douglasbello.Cinelist.dtos.*;
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
    public ResponseEntity<List<TVShowDTO>> findAll() {
        List<TVShowDTO> shows = tvShowService.findAll();
        return ResponseEntity.ok().body(shows);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> findById(@PathVariable UUID id) {
        if (tvShowService.findById(id) != null) {
            TVShowDTO dto = new TVShowDTO(tvShowService.findById(id));
            TVShowDTOResponse response = new TVShowDTOResponse(dto);
            return ResponseEntity.ok().body(response);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RequestResponseDTO(404, "TVShow not found."));
    }

    @GetMapping(value = "/title/{title}")
    public ResponseEntity<?> findTvShowByTitle(@PathVariable String title) {
        if (tvShowService.findByTitle(title) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RequestResponseDTO(404, "TVShow not found."));
        }

        List<TVShowDTOResponse> response = tvShowService.findByTitle(title).stream().map(TVShowDTOResponse::new).collect(Collectors.toList());
        return ResponseEntity.ok().body(response);
    }

    @PostMapping
    public ResponseEntity<?> addTvShow(@RequestBody TVShowDTO tvShowDTO) {
        if (tvShowDTO == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RequestResponseDTO(400, "The show cannot be null."));
        }
        if (tvShowDTO.getTitle().length() == 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RequestResponseDTO(400, "The title of the show cannot be null."));
        }
        if (tvShowDTO.getOverview().length() == 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RequestResponseDTO(400, "The overview of the show cannot be null."));
        }
        tvShowDTO = tvShowService.getDirectorsAndGenres(tvShowDTO);
        tvShowDTO = tvShowService.insert(Mapper.dtoToTVShow(tvShowDTO));
        TVShowDTOResponse response = new TVShowDTOResponse(tvShowDTO);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteTvShow(@PathVariable UUID id) {
        if (id == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RequestResponseDTO(400, "The id cannot be null."));
        }
        if (tvShowService.findById(id) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RequestResponseDTO(404, "TVShow doesn't exists."));
        }
        tvShowService.delete(id);
        return ResponseEntity.ok().body(new RequestResponseDTO(200, "Show deleted."));
    }
}