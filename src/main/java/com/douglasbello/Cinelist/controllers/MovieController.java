package com.douglasbello.Cinelist.controllers;

import com.douglasbello.Cinelist.dtos.*;
import com.douglasbello.Cinelist.dtos.mapper.Mapper;
import com.douglasbello.Cinelist.entities.Movie;
import com.douglasbello.Cinelist.services.ActorService;
import com.douglasbello.Cinelist.services.DirectorService;
import com.douglasbello.Cinelist.services.GenresService;
import com.douglasbello.Cinelist.services.MovieService;
import com.douglasbello.Cinelist.services.UserService;

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
    public ResponseEntity<?> findById(@PathVariable UUID id) {
        if (movieService.findById(id) != null) {
            return ResponseEntity.ok().body(movieService.findById(id));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RequestResponseDTO(HttpStatus.NOT_FOUND.value(), "Movie with this id not found."));
    }

    @GetMapping(value = "/title/{title}")
    public ResponseEntity<?> findByTitle(@PathVariable String title) {
        if (movieService.findMovieByTitle(title) != null)  {
            return ResponseEntity.ok().body(movieService.findMovieByTitle(title));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RequestResponseDTO(HttpStatus.NOT_FOUND.value(), "Movie with this title not found."));
    }

    @PostMapping
    public ResponseEntity<?> addMovie(@RequestBody MovieDTO dto) {
    	Object[] fields = {dto.getTitle(), dto.getOverview(), dto.getReleaseYear()};
    	for (Object field : fields) {
    		if (field.toString().length() == 0) {
    			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RequestResponseDTO(HttpStatus.BAD_REQUEST.value(), "The fields title, overview and release year cannot be blank."));
    		}
    	}
        dto = movieService.getRelatedEntities(dto);
        if (dto.getGenres().isEmpty() || dto.getDirectors().isEmpty() || dto.getActors().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RequestResponseDTO(HttpStatus.BAD_REQUEST.value(), "You must provide at least one genre, one director and one actor that are registered."));
        }
        MovieDTOResponse response = new MovieDTOResponse(movieService.insert(Mapper.dtoToMovie(dto)));
        return ResponseEntity.ok().body(response);
    }
    
    @PostMapping(value = "/rate")
    public ResponseEntity<?> rateMovie(@RequestBody RateDTO dto) {
    	if (dto.movieId() == null || dto.userId() == null || dto.rate() < 1 || dto.rate() > 10) {
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RequestResponseDTO(HttpStatus.BAD_REQUEST.value(), "You must provide the movieId, the userId and your rate of the movie, the rate cannot be less than 1 or bigger than 10."));
    	}
    	if (userService.findById(dto.userId()) == null) {
    		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RequestResponseDTO(HttpStatus.NOT_FOUND.value(), "User not found."));
    	}
    	if (!userService.isCurrentUser(userService.findById(dto.userId()).getUsername())) {
    		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new RequestResponseDTO(HttpStatus.FORBIDDEN.value(), "User unathourized."));
    	}
    	Object[] response = movieService.rateMovie(dto);
    	int statusCode = (int) response[0];
    	if (statusCode != 201) {
    		return ResponseEntity.status(HttpStatus.valueOf(statusCode)).body(new RequestResponseDTO(statusCode, response[1].toString()));
    	}
    	
    	return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/directors/id/{directorId}")
    public ResponseEntity<?> findMoviesByDirectorId(@PathVariable UUID directorId) {
        if (directorService.findMoviesByDirectorId(directorId).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RequestResponseDTO(HttpStatus.NOT_FOUND.value(), "Director doesn't exists or doesn't have any movie registered yet."));
        }
        return ResponseEntity.ok().body(directorService.findMoviesByDirectorId(directorId));
    }

    @GetMapping(value = "/directors/name/{directorName}")
    public ResponseEntity<?> findMoviesByDirectorName(@PathVariable String directorName) {
        if (directorService.findMoviesByDirectorName(directorName) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RequestResponseDTO(HttpStatus.NOT_FOUND.value(), "Director doesn't exists."));
        }
        return ResponseEntity.ok().body(directorService.findMoviesByDirectorName(directorName));
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
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RequestResponseDTO(HttpStatus.NOT_FOUND.value(), "Movie doesn't exists"));
        }
        return ResponseEntity.ok().body(movieService.getMovieActors(movieService.findById(movieId)));
    }

    @GetMapping(value = "/actors/id/{actorId}")
    public ResponseEntity<?> findMoviesByActorId(@PathVariable UUID actorId) {
        if (actorService.findMoviesByActorId(actorId).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RequestResponseDTO(HttpStatus.NOT_FOUND.value(), "Actor doesn't exists or don't have any movie registered."));
        }
        return ResponseEntity.ok().body(actorService.findMoviesByActorId(actorId));
    }

    @GetMapping(value = "/actors/name/{actorName}")
    public ResponseEntity<?> findMoviesByActorName(@PathVariable String actorName) {
        if (actorService.findMoviesByActorName(actorName).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RequestResponseDTO(HttpStatus.NOT_FOUND.value(), "Actor doesn't exists or don't have any movie registered."));
        }
        return ResponseEntity.ok().body(actorService.findMoviesByActorName(actorName));
    }

    @GetMapping(value = "/genres/id/{genreId}")
    public ResponseEntity<?> findMoviesByGenreId(@PathVariable UUID genreId) {
        if (genresService.findMoviesByGenreId(genreId).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RequestResponseDTO(HttpStatus.NOT_FOUND.value(), "Genre doesn't exists or don't have any movie registered."));
        }
        return ResponseEntity.ok().body(genresService.findMoviesByGenreId(genreId));
    }

    @GetMapping(value = "/genres/name/{genreName}")
    public ResponseEntity<?> findMoviesByGenreName(@PathVariable String genreName) {
        if (genresService.findMoviesByGenreName(genreName).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RequestResponseDTO(HttpStatus.NOT_FOUND.value(), "Genre doesn't exists or don't have any movie registered."));
        }
        return ResponseEntity.ok().body(genresService.findMoviesByGenreName(genreName));
    }
}
