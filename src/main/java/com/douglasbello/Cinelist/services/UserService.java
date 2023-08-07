package com.douglasbello.Cinelist.services;

import com.douglasbello.Cinelist.dtos.movie.MovieDTOResponse;
import com.douglasbello.Cinelist.dtos.show.TVShowDTOResponse;
import com.douglasbello.Cinelist.entities.Movie;
import com.douglasbello.Cinelist.entities.TVShow;
import com.douglasbello.Cinelist.entities.User;
import com.douglasbello.Cinelist.dtos.user.UserDTO;
import com.douglasbello.Cinelist.repositories.UserRepository;
import com.douglasbello.Cinelist.services.exceptions.DatabaseException;
import com.douglasbello.Cinelist.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository repository;
    private final MovieService movieService;
    private final TVShowService tvShowService;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserService(UserRepository repository, MovieService movieService, TVShowService tvShowService) {
        this.repository = repository;
        this.movieService = movieService;
        this.tvShowService = tvShowService;
    }

    public List<UserDTO> findAll() {
        return repository.findAll().stream().map(UserDTO::new).collect(Collectors.toList());
    }

    public User findById(UUID id) {
        Optional<User> user = repository.findById(id);
        return user.orElse(null);
    }

    public User findByUsername(String username) {
        return repository.findUserByUsername(username);
    }

    public User findByEmail(String email) {
        return repository.findUserByEmail(email);
    }

    public User signIn(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return repository.save(user);
    }

    public User insert(User user) {
        String password = passwordEncoder.encode(user.getPassword());
        user.setPassword(password);
        return repository.save(user);
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

    public User update(UUID id, User obj) {
        try {
            User entity = repository.getReferenceById(id);
            updateData(entity, obj);
            return repository.save(entity);
        } catch (EntityNotFoundException exception) {
            throw new ResourceNotFoundException(id);
        }
    }

    private void updateData(User entity, User obj) {
        entity.setEmail(obj.getEmail());
        entity.setUsername(obj.getUsername());
    }

    public User addWatchedMovies(User user, Set<UUID> moviesId) {
        if (moviesId.stream().map(movieService::findById).collect(Collectors.toSet()).isEmpty()) {
            return null;
        }
        for (UUID movieId : moviesId) {
            if (movieService.findById(movieId) != null) {
                user.getWatchedMovies().add(movieService.findById(movieId));
            }
        }
        return user;
    }

    public User addFavoriteMovies(User user, Set<UUID> moviesId) {
        if (moviesId.stream().map(movieService::findById).collect(Collectors.toSet()).isEmpty()) {
            return null;
        }
        for (UUID movieId : moviesId) {
            if (movieService.findById(movieId) != null) {
                user.getFavoriteMovies().add(movieService.findById(movieId));
            }
        }
        return user;
    }

    public Set<MovieDTOResponse> getUserWatchedMoviesList(User user) {
        Set<MovieDTOResponse> response = new HashSet<>();
        if (user.getWatchedMovies().isEmpty()) {
            return Collections.emptySet();
        }
        for (Movie movie : user.getWatchedMovies()) {
            response.add(new MovieDTOResponse(movie));
        }
        return response;
    }

    public Set<MovieDTOResponse> getUserFavoriteMoviesList(User user) {
        Set<MovieDTOResponse> response = new HashSet<>();
        if (user.getFavoriteMovies().isEmpty()) {
            return Collections.emptySet();
        }
        for (Movie movie : user.getFavoriteMovies()) {
            response.add(new MovieDTOResponse(movie));
        }
        return response;
    }

    public User addWatchedTvShows(User user, Set<UUID> tvShowsIds) {
        if (tvShowsIds.stream().map(tvShowService::findById).collect(Collectors.toSet()).isEmpty()) {
            return null;
        }
        for (UUID showId : tvShowsIds) {
            user.getWatchedTvShows().add(tvShowService.findById(showId));
        }
        return user;
    }

    public User addFavoriteTvShows(User user, Set<UUID> tvShowsIds) {
        if (tvShowsIds.stream().map(tvShowService::findById).collect(Collectors.toSet()).isEmpty()) {
            return null;
        }
        for (UUID showId : tvShowsIds) {
            user.getFavoriteTvShows().add(tvShowService.findById(showId));
        }
        return user;
    }

    public Set<TVShowDTOResponse> getUserWatchedTvShowsList(User user) {
        Set<TVShowDTOResponse> response = new HashSet<>();
        if (user.getWatchedTvShows().isEmpty()) {
            return Collections.emptySet();
        }
        for (TVShow tvShow : user.getWatchedTvShows()) {
            response.add(new TVShowDTOResponse(tvShow));
        }
        return response;
    }

    public Set<TVShowDTOResponse> getUserFavoriteTvShowsList(User user) {
        Set<TVShowDTOResponse> response = new HashSet<>();
        if (user.getFavoriteTvShows().isEmpty()) {
            return Collections.emptySet();
        }
        for (TVShow tvShow : user.getFavoriteTvShows()) {
            response.add(new TVShowDTOResponse(tvShow));
        }
        return response;
    }

    public User getCurrentUser() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return repository.findUserByUsername(user.getUsername());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByUsername(username);
    }
}
