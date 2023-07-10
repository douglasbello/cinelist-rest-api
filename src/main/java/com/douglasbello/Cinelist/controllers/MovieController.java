package com.douglasbello.Cinelist.controllers;

import com.douglasbello.Cinelist.dtos.*;
import com.douglasbello.Cinelist.dtos.mapper.Mapper;
import com.douglasbello.Cinelist.entities.Movie;
import com.douglasbello.Cinelist.services.MovieService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping(value = "/movies")
public class MovieController {
    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping
    public ResponseEntity<Set<MovieDTOResponse>> findAll() {
        return ResponseEntity.ok().body(movieService.findAll());
    }

    @GetMapping(value = "={id}")
    public ResponseEntity<?> findById(@PathVariable UUID id) {
        if (movieService.findById(id) != null) {
            return ResponseEntity.ok().body(movieService.findById(id));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RequestResponseDTO(404, "Movie with this id not found."));
    }

    @GetMapping(value = "/title={title}")
    public ResponseEntity<?> findByTitle(@PathVariable String title) {
        if (movieService.findMovieByTitle(title) != null)  {
            return ResponseEntity.ok().body(movieService.findMovieByTitle(title));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RequestResponseDTO(HttpStatus.NOT_FOUND.value(), "Movie with this title not found."));
    }

    @GetMapping(value = "/director={directorId}")
    public ResponseEntity<?> findMoviesByDirectorId(@PathVariable UUID directorId) {
        if (!movieService.findMoviesByDirectorId(directorId).isEmpty()) {
            return ResponseEntity.ok().body(movieService.findMoviesByDirectorId(directorId));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RequestResponseDTO(HttpStatus.NOT_FOUND.value(), "Director doesn't exists or doesn't have any movie registered yet."));
    }

    @PostMapping()
    public ResponseEntity<?> addMovie(@RequestBody MovieDTO dto) {
        if (dto == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RequestResponseDTO(HttpStatus.BAD_REQUEST.value(), "The movie cannot be null."));
        }
        if (dto.getTitle().length() < 1) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RequestResponseDTO(HttpStatus.BAD_REQUEST.value(), "The movie title cannot be null."));
        }
        if (dto.getOverview().length() < 1) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RequestResponseDTO(HttpStatus.BAD_REQUEST.value(), "The movie overview cannot be null."));
        }

        dto = movieService.getRelatedEntities(dto);
        if (dto.getGenres().size() == 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RequestResponseDTO(HttpStatus.BAD_REQUEST.value(), "You must provide at least one genre that is registered."));
        }
        if (dto.getDirectors().size() == 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RequestResponseDTO(HttpStatus.BAD_REQUEST.value(), "You must provide at least one director that is registered.."));
        }
        MovieDTOResponse response = new MovieDTOResponse(movieService.insert(Mapper.dtoToMovie(dto)));
        return ResponseEntity.ok().body(response);
    }

    @PostMapping(value = "/{movieId}/actors")
    public ResponseEntity<?> addActorsToMovie(@PathVariable UUID movieId, @RequestBody Set<UUID> actorsIds) {
        if (movieService.findById(movieId) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RequestResponseDTO(HttpStatus.NOT_FOUND.value(), "Movie not found."));
        }
        if (actorsIds.size() == 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RequestResponseDTO(HttpStatus.BAD_REQUEST.value(), "You must provide at least one actor id."));
        }
        if (movieService.addActorsToMovie(movieService.findById(movieId), actorsIds) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RequestResponseDTO(HttpStatus.NOT_FOUND.value(), "Actors not found."));
        }
        Movie movie = movieService.findById(movieId);
        movie = movieService.addActorsToMovie(movie, actorsIds);
        movie = movieService.update(movie.getId(), movie);
        return ResponseEntity.ok().body(movieService.getMovieActors(movie));
    }

    @GetMapping(value = "/{movieId}/actors")
    public ResponseEntity<?> getMovieActors(@PathVariable UUID movieId) {
        if (movieService.findById(movieId) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RequestResponseDTO(HttpStatus.NOT_FOUND.value(), "User doesn't exists"));
        }
        return ResponseEntity.ok().body(movieService.getMovieActors(movieService.findById(movieId)));
    }
}
