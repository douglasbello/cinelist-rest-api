package com.douglasbello.Cinelist.controllers;

import com.douglasbello.Cinelist.dtos.*;
import com.douglasbello.Cinelist.dtos.mapper.Mapper;
import com.douglasbello.Cinelist.services.TVShowService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping(value = "/tvshows")
public class TVShowController {
    private final TVShowService tvShowService;

    public TVShowController(TVShowService tvShowService) {
	    this.tvShowService = tvShowService;
    }

    @GetMapping
    public ResponseEntity<List<TVShowDTOResponse>> findAll() {
        List<TVShowDTOResponse> shows = tvShowService.findAll();
        return ResponseEntity.ok().body(shows);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> findById(@PathVariable UUID id) {
        if (tvShowService.findById(id) != null) {
            TVShowDTO dto = new TVShowDTO(tvShowService.findById(id));
            TVShowDTOResponse response = new TVShowDTOResponse(dto);
            return ResponseEntity.ok().body(response);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RequestResponseDTO(HttpStatus.NOT_FOUND.value(), "TVShow not found."));
    }

    @GetMapping(value = "/title/{title}")
    public ResponseEntity<?> findTvShowByTitle(@PathVariable String title) {
        if (tvShowService.findByTitle(title).size() == 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RequestResponseDTO(HttpStatus.NOT_FOUND.value(), "TVShow not found."));
        }

        Set<TVShowDTOResponse> response = tvShowService.findByTitle(title);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping
    public ResponseEntity<?> addTvShow(@RequestBody TVShowDTO tvShowDTO) {
        if (tvShowDTO == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RequestResponseDTO(HttpStatus.BAD_REQUEST.value(), "The show cannot be null."));
        }
        if (tvShowDTO.getTitle().length() == 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RequestResponseDTO(HttpStatus.BAD_REQUEST.value(), "The title of the show cannot be null."));
        }
        if (tvShowDTO.getOverview().length() == 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RequestResponseDTO(HttpStatus.BAD_REQUEST.value(), "The overview of the show cannot be null."));
        }
        tvShowDTO = tvShowService.getDirectorsAndGenresAndActors(tvShowDTO);
        TVShowDTOResponse response = tvShowService.insert(Mapper.dtoToTVShow(tvShowDTO));
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteTvShow(@PathVariable UUID id) {
        if (id == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RequestResponseDTO(HttpStatus.BAD_REQUEST.value(), "The id cannot be null."));
        }
        if (tvShowService.findById(id) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RequestResponseDTO(HttpStatus.NOT_FOUND.value(), "TVShow doesn't exists."));
        }
        tvShowService.delete(id);
        return ResponseEntity.ok().body(new RequestResponseDTO(200, "Show deleted."));
    }

    @GetMapping(value = "/director/id/{directorId}")
    public ResponseEntity<?> findTvShowsByDirectorId(@PathVariable UUID directorId) {

        if (tvShowService.findTvShowsByDirectorId(directorId).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RequestResponseDTO(HttpStatus.NOT_FOUND.value(), "Director doesn't exists or has no shows registered."));
        }
        return ResponseEntity.ok().body(tvShowService.findTvShowsByDirectorId(directorId));
    }

    @GetMapping(value = "/actor/name/{directorName}")
    public ResponseEntity<?> findTvShowsByDirectorName(@PathVariable String directorName) {
        if (tvShowService.findTvShowsByDirectorName(directorName) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RequestResponseDTO(HttpStatus.NOT_FOUND.value(), "Director not found."));
        }
        return ResponseEntity.ok().body(tvShowService.findTvShowsByActorName(directorName));
    }

    @GetMapping(value = "/actor/id/{actorId}")
    public ResponseEntity<?> findTvShowsByActorId(@PathVariable UUID actorId) {
        if (tvShowService.findTvShowsByActorId(actorId).size() != 0) {
            return ResponseEntity.ok().body(tvShowService.findTvShowsByDirectorId(actorId));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RequestResponseDTO(HttpStatus.NOT_FOUND.value(), "Actor doesn't exists or has no shows registered."));
    }

}