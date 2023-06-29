package com.douglasbello.Cinelist.controllers;

import com.douglasbello.Cinelist.dto.Mapper;
import com.douglasbello.Cinelist.dto.RequestResponseDTO;
import com.douglasbello.Cinelist.dto.TVShowDTO;
import com.douglasbello.Cinelist.services.TVShowService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
        List<TVShowDTO> shows = tvShowService.findAll().stream().map(tv -> new TVShowDTO(
                tv.getId(), tv.getTitle(), tv.getOverview(), tv.getReleaseYear(),  tv.getGenre(),tv.getDirectors(), tv.getSeasonsAndEpisodes())
        ).collect(Collectors.toList());
        return ResponseEntity.ok().body(shows);
    }

    @PostMapping
    public ResponseEntity<?> addTvShow(@RequestBody TVShowDTO tvShowDTO) {
        if (tvShowDTO == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RequestResponseDTO(400, "The show cannot be null."));
        }
        return ResponseEntity.ok().body(tvShowService.insert(Mapper.tvShowDtoToEntity(tvShowDTO)));
    }
}