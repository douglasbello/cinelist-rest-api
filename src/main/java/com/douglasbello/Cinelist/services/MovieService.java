package com.douglasbello.Cinelist.services;

import com.douglasbello.Cinelist.dtos.ActorDTO;
import com.douglasbello.Cinelist.dtos.MovieDTO;
import com.douglasbello.Cinelist.dtos.MovieDTOResponse;
import com.douglasbello.Cinelist.dtos.RateDTO;
import com.douglasbello.Cinelist.entities.Actor;
import com.douglasbello.Cinelist.entities.Director;
import com.douglasbello.Cinelist.entities.Movie;
import com.douglasbello.Cinelist.entities.User;
import com.douglasbello.Cinelist.repositories.MovieRepository;
import com.douglasbello.Cinelist.services.exceptions.DatabaseException;
import com.douglasbello.Cinelist.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MovieService {
    private final MovieRepository repository;
    private final DirectorService directorService;
    private final GenresService genresService;
    private final ActorService actorService;
    private final UserService userService;

    public MovieService(MovieRepository repository, DirectorService directorService, GenresService genresService, ActorService actorService, UserService userService) {
        this.repository = repository;
        this.directorService = directorService;
        this.genresService = genresService;
        this.actorService = actorService;
        this.userService = userService;
    }

    public Set<MovieDTOResponse> findAll() {
        return repository.findAll().stream().map(MovieDTOResponse::new).collect(Collectors.toSet());
    }

    public Movie findById(UUID id) {
        Optional<Movie> obj = repository.findById(id);
        return obj.orElse(null);
    }

    public Movie insert(Movie movie) {
        return repository.save(movie);
    }

    public void delete(UUID id) {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException emptyResultDataAccessException) {
            throw new ResourceNotFoundException(id);
        } catch (DataIntegrityViolationException dataIntegrityViolationException) {
            throw new DatabaseException(dataIntegrityViolationException.getMessage());
        }
    }

    public Movie update(UUID id,Movie obj) {
        try {
            Movie entity = repository.getReferenceById(id);
            updateData(entity,obj);
            return repository.save(entity);
        } catch (EntityNotFoundException exception) {
            throw new ResourceNotFoundException(id);
        }
    }

    private void updateData(Movie entity, Movie obj) {
        entity.setTitle(obj.getTitle());
        entity.setOverview(obj.getOverview());
    }

    public Set<MovieDTOResponse> findMovieByTitle(String title) {
        title = title.replace("-", " ");
        if (!repository.findMovieByTitleContainingIgnoreCase(title).isEmpty()) {
            return repository.findMovieByTitleContainingIgnoreCase(title).stream().map(MovieDTOResponse::new).collect(Collectors.toSet());
        }
        return null;
    }

    public Set<ActorDTO> getMovieActors(Movie movie) {
        Set<ActorDTO> response = new HashSet<>();
        if (movie.getActors().isEmpty()) {
            return Collections.emptySet();
        }
        for (Actor actor : movie.getActors()) {
            response.add(new ActorDTO(actor));
        }
        return response;
    }

    public Movie addActorsToMovie(Movie movie, Set<UUID> actorsIds) {
        if (actorsIds.stream().map(actorService::findById).collect(Collectors.toSet()).isEmpty()) {
            return null;
        }
        for (UUID showId : actorsIds) {
            movie.getActors().add(actorService.findById(showId));
        }
        return movie;
    }

    public MovieDTO getRelatedEntities(MovieDTO movieDTO) {
        // this if is verifying if the directorIds, genresIds and actorsIds will return empty collections, if all three return empty collections the method just return the dto
        if (movieDTO.getDirectorsIds().stream().map(directorService::findById).collect(Collectors.toSet()).size() == 0 &&
            movieDTO.getGenresIds().stream().map(genresService::findById).toList().size() == 0 &&
            movieDTO.getActors().stream().map(a -> actorService.findById(a.getId())).collect(Collectors.toSet()).size() == 0) {
            return movieDTO;
        }
        for (UUID directorId : movieDTO.getDirectorsIds()) {
            if (directorService.findById(directorId) != null) {
                movieDTO.getDirectors().add(directorService.findById(directorId));
            }
        }
        for (UUID genresId : movieDTO.getGenresIds()) {
            if (genresService.findById(genresId) != null) {
                movieDTO.getGenres().add(genresService.findById(genresId));
            }
        }
        for (UUID actorId : movieDTO.getActorsIds()) {
            if (actorService.findById(actorId) != null) {
                movieDTO.getActors().add(actorService.findById(actorId));
            }
        }
        return movieDTO;
    }
    
    public Object[] rateMovie(RateDTO dto) {
    	Object[] response = new Object[2];
    	if (userService.findById(dto.userId()) != null && this.findById(dto.movieId()) != null) {
    		Movie movie = this.findById(dto.movieId());
    		Map<UUID, Double> ratings = movie.getRatings();
    		if (ratings.containsKey(dto.userId())) {
    			ratings.remove(dto.userId());
    		}
    		ratings.put(dto.userId(), dto.rate());
    		movie.setRate();
    		this.update(movie.getId(), movie);
    		response[0] = HttpStatus.OK.value();
    		return response;
    	}
    	response[0] = HttpStatus.NOT_FOUND.value();
    	response[1] = "User or movie not found.";
    	return response;
    }
}
