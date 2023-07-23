package com.douglasbello.Cinelist.services;

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
import org.springframework.http.HttpStatus;
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
        List<UserDTO> dtos = repository.findAll().stream().map(UserDTO::new).collect(Collectors.toList());
        return dtos;
    }

    public User findById(UUID id) {
        Optional<User> user = repository.findById(id);
        return user.orElse(null);
    }

    public User findByUsername(String username) {
        return repository.findUserByUsername(username);
    }

    public User signIn(UserDTO dto) {
        User user = Mapper.dtoToUser(dto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
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
    
    public User addFavoriteMovies(User user, Set<UUID> moviesId) {
        if (moviesId.stream().map(movieService::findById).collect(Collectors.toSet()).size() == 0) {
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
        if (user.getWatchedMovies().size() == 0) {
            return Collections.emptySet();
        }
        for (Movie movie : user.getWatchedMovies()) {
            response.add(new MovieDTOResponse(movie));
        }
        return response;
    }
    
    public Set<MovieDTOResponse> getUserFavoriteMoviesList(User user) {
        Set<MovieDTOResponse> response = new HashSet<>();
        if (user.getFavoriteMovies().size() == 0) {
            return Collections.emptySet();
        }
        for (Movie movie : user.getFavoriteMovies()) {
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
    
    public User addFavoriteTvShows(User user, Set<UUID> tvShowsIds) {
        if (tvShowsIds.stream().map(tvShowService::findById).collect(Collectors.toSet()).size() == 0) {
            return null;
        }
        for (UUID showId : tvShowsIds) {
            user.getFavoriteTvShows().add(tvShowService.findById(showId));
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
    
    public Set<TVShowDTOResponse> getUserFavoriteTvShowsList(User user) {
        Set<TVShowDTOResponse> response = new HashSet<>();
        if (user.getFavoriteTvShows().size() == 0) {
            return Collections.emptySet();
        }
        for (TVShow tvShow : user.getFavoriteTvShows()) {
            response.add(new TVShowDTOResponse(tvShow));
        }
        return response;
    }

    public User insert(User user) {
        String password = passwordEncoder.encode(user.getPassword());
        user.setPassword(password);
        return repository.save(user);
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
    
    public Object[] validateUserDto(UserDTO obj) {
    	Object[] errors = new Object[2];
    	if (obj.getEmail() == null || obj.getUsername() == null) {
    		errors[0] = HttpStatus.BAD_REQUEST.value();
    		errors[1] = "The email and username cannot be null.";
    		return errors;
    	}
        if (obj.getEmail().length() < 15) {
        	errors[0] = HttpStatus.BAD_REQUEST.value();
        	errors[1] = "Email cannot be shorter than 15 characters.";
        	return errors;
        }
        if (checkIfTheEmailIsAlreadyInUse(obj.getEmail())) {
        	errors[0] = HttpStatus.BAD_REQUEST.value();
        	errors[1] = "Email is already in use.";
        	return errors;
        }
        if (obj.getUsername().length() < 4 || obj.getUsername().length() > 16) {
        	errors[0] = HttpStatus.BAD_REQUEST.value();
        	errors[1] = "Username cannot be shorter than 4 characters or bigger than 16.";
        	return errors;
        }
        if (checkIfTheUsernameIsAlreadyInUse(obj.getUsername())) {
        	errors[0] = HttpStatus.CONFLICT.value();
        	errors[1] = "Username is already in use.";
        	return errors;
        }
    	if (obj.getPassword() == null) {
    		errors[0] = HttpStatus.BAD_REQUEST.value();
    		errors[1] = "The password cannot be null.";
    		return errors;
    	}
        if (obj.getUsername().contains(" ")) {
        	errors[0] = HttpStatus.BAD_REQUEST.value();
        	errors[1] = "The username cannot contain spaces.";
        	return errors;
        }
        if (obj.getPassword().length() < 8 || obj.getPassword().length() > 100) {
        	errors[0] = HttpStatus.BAD_REQUEST.value();
        	errors[1] = "Password cannot be less than 8 or bigger than 100.";
        	return errors;
        }
        if (obj.getGender() < 1 || obj.getGender() > 3) {
        	errors[0] = HttpStatus.BAD_REQUEST.value();
        	errors[1] = "Gender code cannot be bigger than 3 or less than 1.";
        	return errors;
        }
        errors[0] = HttpStatus.OK.value();
        return errors;
    }
    
    public boolean isCurrentUser(String username) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return currentUser.getUsername().equals(username);
    }

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return repository.findByUsername(username);
	}
}
