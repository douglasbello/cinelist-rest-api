package com.douglasbello.Cinelist.controllers;

import com.douglasbello.Cinelist.dtos.*;
import com.douglasbello.Cinelist.dtos.mapper.Mapper;
import com.douglasbello.Cinelist.services.ActorService;
import com.douglasbello.Cinelist.services.DirectorService;
import com.douglasbello.Cinelist.services.GenresService;
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
    private final ActorService actorService;
    private final DirectorService directorService;
    private final GenresService genresService;

    public TVShowController(TVShowService tvShowService, ActorService actorService, DirectorService directorService, GenresService genresService) {
	    this.tvShowService = tvShowService;
        this.actorService = actorService;
        this.directorService = directorService;
        this.genresService = genresService;
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
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RequestResponseDTO(HttpStatus.NOT_FOUND.value(), "Show not found."));
    }

    @GetMapping(value = "/title/{title}")
    public ResponseEntity<?> findTvShowsByTitle(@PathVariable String title) {
        if (tvShowService.findByTitle(title).size() == 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RequestResponseDTO(HttpStatus.NOT_FOUND.value(), "Show not found."));
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
        tvShowDTO = tvShowService.getRelatedEntities(tvShowDTO);
        TVShowDTOResponse response = tvShowService.insert(Mapper.dtoToTVShow(tvShowDTO));
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteTvShow(@PathVariable UUID id) {
        if (id == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RequestResponseDTO(HttpStatus.BAD_REQUEST.value(), "The id cannot be null."));
        }
        if (tvShowService.findById(id) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RequestResponseDTO(HttpStatus.NOT_FOUND.value(), "Show doesn't exists."));
        }
        tvShowService.delete(id);
        return ResponseEntity.ok().body(new RequestResponseDTO(HttpStatus.OK.value(), "Show deleted."));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateTvShow(@PathVariable UUID id, @RequestBody TVShowDTO dto) {
        if (dto == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RequestResponseDTO(HttpStatus.BAD_REQUEST.value(), "The show cannot be null."));
        }
        if (dto.getTitle().length() == 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RequestResponseDTO(HttpStatus.BAD_REQUEST.value(), "The title of the show cannot be null."));
        }
        if (dto.getOverview().length() == 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RequestResponseDTO(HttpStatus.BAD_REQUEST.value(), "The overview of the show cannot be null."));
        }
        if (tvShowService.findById(id) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RequestResponseDTO(HttpStatus.BAD_REQUEST.value(), "Show not found."));
        }
        dto = tvShowService.getRelatedEntities(dto);
        TVShowDTOResponse response = tvShowService.update(id, Mapper.dtoToTVShow(dto));
        return ResponseEntity.ok().body(response);
    }

    @GetMapping(value = "/{showId}/actors")
    public ResponseEntity<?> getShowActors(@PathVariable UUID showId) {
        if (tvShowService.findById(showId) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RequestResponseDTO(HttpStatus.NOT_FOUND.value(), "Show not found."));
        }
        return ResponseEntity.ok().body(tvShowService.getShowActors(tvShowService.findById(showId)));
    }

    @GetMapping(value = "/directors/id/{directorId}")
    public ResponseEntity<?> findTvShowsByDirectorId(@PathVariable UUID directorId) {
        if (directorService.findShowsByDirectorId(directorId).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RequestResponseDTO(HttpStatus.NOT_FOUND.value(), "Director doesn't exists or has no shows registered."));
        }
        return ResponseEntity.ok().body(tvShowService.findTvShowsByDirectorId(directorId));
    }

    @GetMapping(value = "/actors/name/{directorName}")
    public ResponseEntity<?> findTvShowsByDirectorName(@PathVariable String directorName) {
        if (directorService.findShowsByDirectorName(directorName).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RequestResponseDTO(HttpStatus.NOT_FOUND.value(), "Director not found or has no shows registered."));
        }
        return ResponseEntity.ok().body(tvShowService.findTvShowsByActorName(directorName));
    }

    @GetMapping(value = "/actors/id/{actorId}")
    public ResponseEntity<?> findTvShowsByActorId(@PathVariable UUID actorId) {
        if (actorService.findShowsByActorId(actorId).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RequestResponseDTO(HttpStatus.NOT_FOUND.value(), "Actor doesn't exists or has no shows registered."));
        }
        return ResponseEntity.ok().body(tvShowService.findTvShowsByDirectorId(actorId));
    }

    @GetMapping(value = "/actors/name/{actorName}")
    public ResponseEntity<?> findTvShowsByActorName(@PathVariable String actorName) {
        if (actorService.findShowsByActorName(actorName).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RequestResponseDTO(HttpStatus.NOT_FOUND.value(), "Actor doesn't exists or has no shows registered."));
        }
        return ResponseEntity.ok().body(tvShowService.findTvShowsByActorName(actorName));
    }

    @GetMapping(value = "/genres/id/{genreId}")
    public ResponseEntity<?> findTvShowsByGenreId(@PathVariable UUID genreId) {
        if (genresService.findShowsByGenreId(genreId).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RequestResponseDTO(HttpStatus.NOT_FOUND.value(), "Genre doesn't exists or has no shows registered."));
        }
        return ResponseEntity.ok().body(genresService.findShowsByGenreId(genreId));
    }

    @GetMapping(value = "/genres/id/{genreName}")
    public ResponseEntity<?> findTvShowsByGenreName(@PathVariable String genreName) {
        if (genresService.findShowsByGenreName(genreName).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RequestResponseDTO(HttpStatus.NOT_FOUND.value(), "Genre doesn't exists or has no shows registered."));
        }
        return ResponseEntity.ok().body(genresService.findShowsByGenreName(genreName));
    }

}