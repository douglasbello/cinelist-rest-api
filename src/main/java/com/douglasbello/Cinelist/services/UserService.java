package com.douglasbello.Cinelist.services;

import com.douglasbello.Cinelist.dtos.MovieDTO;
import com.douglasbello.Cinelist.dtos.MovieDTOResponse;
import com.douglasbello.Cinelist.dtos.TVShowDTOResponse;
import com.douglasbello.Cinelist.dtos.mapper.Mapper;
import com.douglasbello.Cinelist.entities.Movie;
import com.douglasbello.Cinelist.entities.TVShow;
import com.douglasbello.Cinelist.entities.User;
import com.douglasbello.Cinelist.dtos.UserDTO;
import com.douglasbello.Cinelist.repositories.UserRepository;
import com.douglasbello.Cinelist.services.exceptions.DatabaseException;
import com.douglasbello.Cinelist.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {
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
        List<UserDTO> dtos = repository.findAll().stream().map(UserDTO::new).collect(Collectors.toList());
        return dtos;
    }

    public User findById(UUID id) {
        Optional<User> user = repository.findById(id);
        return user.orElse(null);
    }

    public User addWatchedMovies(User user, Set<UUID> moviesId) {
        if (moviesId.stream().map(movieService::findById).collect(Collectors.toSet()).size() == 0) {
            return null;
        }
        for (UUID movieId : moviesId) {
            if (movieService.findById(movieId) != null) {
                user.getWatchedMovies().add(movieService.findById(movieId));
            }
        }
        return user;
    }

    public Set<MovieDTOResponse> getUserWatchedMoviesList(User user) {
        Set<MovieDTOResponse> response = new HashSet<>();
        if (user.getWatchedMovies().size() == 0) {
            return Collections.emptySet();
        }
        for (Movie movie : user.getWatchedMovies()) {
            response.add(new MovieDTOResponse(movie));
        }
        return response;
    }

    public User addWatchedTvShows(User user, Set<UUID> tvShowsIds) {
        if (tvShowsIds.stream().map(tvShowService::findById).collect(Collectors.toSet()).size() == 0) {
            return null;
        }
        for (UUID showId : tvShowsIds) {
            user.getWatchedTvShows().add(tvShowService.findById(showId));
        }
        return user;
    }

    public Set<TVShowDTOResponse> getUserWatchedTvShowsList(User user) {
        Set<TVShowDTOResponse> response = new HashSet<>();
        if (user.getWatchedTvShows().size() == 0) {
            return Collections.emptySet();
        }
        for (TVShow tvShow : user.getWatchedTvShows()) {
            response.add(new TVShowDTOResponse(tvShow));
        }
        return response;
    }

    public User insert(User user) {
        String password = passwordEncoder.encode(user.getPassword());
        user.setPassword(password);
        return repository.save(user);
    }

    public User signIn(UserDTO dto) {
        User user = Mapper.dtoToUser(dto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return repository.save(user);
    }

    public UserDTO login(UserDTO obj) {
        try {
            User entity;
            if (obj.getEmail() == null) {
                entity = repository.findUserByUsername(obj.getUsername());
            }
            else {
                entity = repository.findUserByEmail(obj.getEmail());
            }

            if (entity == null) {
                return null;
            }

            if (passwordEncoder.matches(obj.getPassword(), entity.getPassword())) {
                return new UserDTO(entity);
            }

        } catch (NoSuchElementException exception) {
            exception.printStackTrace();
            return null;
        }

        return null;
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

    public User update(UUID id,User obj) {
        try {
            User entity = repository.getReferenceById(id);
            updateData(entity,obj);
            return repository.save(entity);
        } catch (EntityNotFoundException exception) {
            throw new ResourceNotFoundException(id);
        }
    }

    private void updateData(User entity, User obj) {
        entity.setEmail(obj.getEmail());
        entity.setUsername(obj.getUsername());
    }

    public boolean checkIfTheUsernameIsAlreadyInUse(String username) {
        if (repository.findUserByUsername(username) == null) {
            return false;
        }
        return true;
    }

    public boolean checkIfTheEmailIsAlreadyInUse(String email) {
        if (repository.findUserByEmail(email) == null) {
            return false;
        }
        return true;
    }
}
