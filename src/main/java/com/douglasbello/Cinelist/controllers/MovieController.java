package com.douglasbello.Cinelist.controllers;

import com.douglasbello.Cinelist.dtos.*;
import com.douglasbello.Cinelist.dtos.Mapper;
import com.douglasbello.Cinelist.dtos.actor.ActorDTO;
import com.douglasbello.Cinelist.dtos.movie.MovieDTO;
import com.douglasbello.Cinelist.dtos.movie.MovieDTOResponse;
import com.douglasbello.Cinelist.entities.Movie;
import com.douglasbello.Cinelist.entities.User;
import com.douglasbello.Cinelist.services.ActorService;
import com.douglasbello.Cinelist.services.DirectorService;
import com.douglasbello.Cinelist.services.GenresService;
import com.douglasbello.Cinelist.services.MovieService;
import com.douglasbello.Cinelist.services.UserService;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping(value = "/movies")
public class MovieController {
    private final MovieService movieService;
    private final ActorService actorService;
    private final DirectorService directorService;
    private final GenresService genresService;
    private final UserService userService;

    public MovieController(MovieService movieService, ActorService actorService, DirectorService directorService, GenresService genresService, UserService userService) {
        this.movieService = movieService;
        this.actorService = actorService;
        this.directorService = directorService;
        this.genresService = genresService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<Set<MovieDTOResponse>> findAll() {
        return ResponseEntity.ok().body(movieService.findAll());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<MovieDTOResponse> findById(@PathVariable UUID id) {
        MovieDTOResponse dto = new MovieDTOResponse(movieService.findById(id));
        return ResponseEntity.ok(dto);
    }

    @GetMapping(value = "/title/{title}")
    public ResponseEntity<Set<MovieDTOResponse>> findByTitle(@PathVariable String title) {
        return ResponseEntity.ok().body(movieService.findMovieByTitle(title));
    }

    @PostMapping
    public ResponseEntity<MovieDTOResponse> addMovie(@Valid @RequestBody MovieDTO dto) {
        dto = movieService.getRelatedEntities(dto);
        MovieDTOResponse response = new MovieDTOResponse(movieService.insert(Mapper.dtoToMovie(dto)));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping(value = "/rate")
    public ResponseEntity<Void> rateMovie(@Valid @RequestBody RateDTO dto) {
        User currentUser = userService.getCurrentUser();
        movieService.rateMovie(dto, currentUser);

        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/directors/id/{directorId}")
    public ResponseEntity<Set<MovieDTOResponse>> findMoviesByDirectorId(@PathVariable UUID directorId) {
        return ResponseEntity.ok().body(directorService.findMoviesByDirectorId(directorId));
    }

    @GetMapping(value = "/directors/name/{directorName}")
    public ResponseEntity<Set<MovieDTOResponse>> findMoviesByDirectorName(@PathVariable String directorName) {
        return ResponseEntity.ok().body(directorService.findMoviesByDirectorName(directorName));
    }

    @PostMapping(value = "/{movieId}/actors")
    public ResponseEntity<Set<ActorDTO>> addActorsToMovie(@PathVariable UUID movieId, @RequestBody Set<UUID> actorsIds) {
        Movie movie = movieService.findById(movieId);
        movie = movieService.addActorsToMovie(movie, actorsIds);
        movie = movieService.update(movie.getId(), movie);
        return ResponseEntity.ok().body(movieService.getMovieActors(movie));
    }

    @GetMapping(value = "/{movieId}/actors")
    public ResponseEntity<Set<ActorDTO>> getMovieActors(@PathVariable UUID movieId) {
        return ResponseEntity.ok().body(movieService.getMovieActors(movieService.findById(movieId)));
    }

    @GetMapping(value = "/actors/id/{actorId}")
    public ResponseEntity<Set<MovieDTOResponse>> findMoviesByActorId(@PathVariable UUID actorId) {
        return ResponseEntity.ok().body(actorService.findMoviesByActorId(actorId));
    }

    @GetMapping(value = "/actors/name/{actorName}")
    public ResponseEntity<Set<MovieDTOResponse>> findMoviesByActorName(@PathVariable String actorName) {
        return ResponseEntity.ok().body(actorService.findMoviesByActorName(actorName));
    }

    @GetMapping(value = "/genres/id/{genreId}")
    public ResponseEntity<Set<MovieDTOResponse>> findMoviesByGenreId(@PathVariable UUID genreId) {
        return ResponseEntity.ok().body(genresService.findMoviesByGenreId(genreId));
    }

    @GetMapping(value = "/genres/name/{genreName}")
    public ResponseEntity<Set<MovieDTOResponse>> findMoviesByGenreName(@PathVariable String genreName) {
        return ResponseEntity.ok().body(genresService.findMoviesByGenreName(genreName));
    }
}
