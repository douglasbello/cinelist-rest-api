package com.douglasbello.Cinelist.controllers;

import com.douglasbello.Cinelist.dtos.*;
import com.douglasbello.Cinelist.entities.Comment;
import com.douglasbello.Cinelist.entities.Movie;
import com.douglasbello.Cinelist.entities.User;
import com.douglasbello.Cinelist.repositories.MovieRepository;
import com.douglasbello.Cinelist.services.CommentService;
import com.douglasbello.Cinelist.services.MovieService;
import com.douglasbello.Cinelist.services.TVShowService;
import com.douglasbello.Cinelist.services.UserService;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RequestResponseDTO(404, "Movie with this title not found."));
    }

    @GetMapping(value = "/director={directorId}")
    public ResponseEntity<?> findMoviesByDirectorId(@PathVariable UUID directorId) {
        if (!movieService.findMoviesByDirectorId(directorId).isEmpty()) {
            return ResponseEntity.ok().body(movieService.findMoviesByDirectorId(directorId));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RequestResponseDTO(404, "Director doesn't exists or doesn't have any movie registered yet."));
    }

    @PostMapping(value = "/add")
    public ResponseEntity<?> addMovie(@RequestBody MovieDTO dto) {
        if (dto == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RequestResponseDTO(400, "The movie cannot be null."));
        }
        if (dto.getTitle().length() < 1) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RequestResponseDTO(400, "The movie title cannot be null."));
        }
        if (dto.getOverview().length() < 1) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RequestResponseDTO(400, "The movie overview cannot be null."));
        }

        dto = movieService.getDirectorsAndGenres(dto);
        if (dto.getGenres().size() == 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RequestResponseDTO(400, "You have to pass at least one genre that is registered."));
        }
        if (dto.getDirectors().size() == 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RequestResponseDTO(400, "You have to pass at least one director that is registered.."));
        }
        MovieDTOResponse response = new MovieDTOResponse(movieService.insert(Mapper.dtoToMovie(dto)));
        return ResponseEntity.ok().body(response);
    }
}
