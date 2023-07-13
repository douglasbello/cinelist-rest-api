package com.douglasbello.Cinelist.controllers;

import com.douglasbello.Cinelist.dtos.RequestResponseDTO;
import com.douglasbello.Cinelist.dtos.UserDTO;
import com.douglasbello.Cinelist.entities.User;
import com.douglasbello.Cinelist.services.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping(value = "/users")
public class UserController {
	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAll() {
        return ResponseEntity.ok().body(userService.findAll());
    }

	@PostMapping(value = "/sign-in")
	public ResponseEntity<?> signIn(@RequestBody UserDTO obj) {
		Object[] errors = userService.validateUserDto(obj);
		int errorCode = (int) errors[0];
		if (errorCode != HttpStatus.OK.value()) {
			return ResponseEntity.status(HttpStatus.valueOf(errorCode)).body(new RequestResponseDTO(errorCode, errors[1].toString()));
		}
        userService.signIn(obj);
        return ResponseEntity.status(HttpStatus.CREATED).body(new RequestResponseDTO(HttpStatus.CREATED.value(), "Account created successfully!"));
	}

    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@RequestBody UserDTO obj) {
        if (obj.getEmail() == null && obj.getUsername() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RequestResponseDTO(HttpStatus.BAD_REQUEST.value(), "You have to pass the email or the username."));
        }
        if (obj.getEmail() != null && obj.getUsername() != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RequestResponseDTO(HttpStatus.BAD_REQUEST.value(),"You can only pass the email or username for login, not both."));
        }
        if (userService.login(obj) == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new RequestResponseDTO(HttpStatus.FORBIDDEN.value(), "Username or password incorrect"));
        }
        return ResponseEntity.ok().body(userService.login(obj));
    }

    @GetMapping(value = "/{userId}/movies")
    public ResponseEntity<?> getUserWatchedMovies(@PathVariable UUID userId) {
        if (userService.findById(userId) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RequestResponseDTO(HttpStatus.NOT_FOUND.value(), "User doesn't exists."));
        }
        return ResponseEntity.ok().body(userService.getUserWatchedMoviesList(userService.findById(userId)));
    }

    @PostMapping(value = "/{userId}/movies")
    public ResponseEntity<?> addMoviesToWatchedList(@PathVariable UUID userId ,@RequestBody Set<UUID> moviesId) {
        if (userService.findById(userId) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RequestResponseDTO(HttpStatus.NOT_FOUND.value(), "User not found."));
        }
        if (moviesId.size() == 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RequestResponseDTO(HttpStatus.BAD_REQUEST.value(), "You need to pass at least one movie id."));
        }
        if (userService.addWatchedMovies(userService.findById(userId), moviesId) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RequestResponseDTO(HttpStatus.NOT_FOUND.value(), "Movies not found."));
        }

        User user = userService.findById(userId);
        user = userService.addWatchedMovies(user, moviesId);
        user = userService.update(user.getId(), user);
        return ResponseEntity.ok().body(userService.getUserWatchedMoviesList(user));
    }

    @GetMapping(value = "/{userId}/shows")
    public ResponseEntity<?> getUserWatchedShows(@PathVariable UUID userId) {
        if (userService.findById(userId) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RequestResponseDTO(HttpStatus.NOT_FOUND.value(), "User doesn't exists."));
        }
        return ResponseEntity.ok().body(userService.getUserWatchedTvShowsList(userService.findById(userId)));
    }

    @PostMapping(value = "/{userId}/shows")
    public ResponseEntity<?> addTvShowsToWatchedList(@PathVariable UUID userId, @RequestBody Set<UUID> tvShowsIds) {
        if (userService.findById(userId) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RequestResponseDTO(HttpStatus.NOT_FOUND.value(), "User not found."));
        }
        if (tvShowsIds.size() == 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RequestResponseDTO(HttpStatus.BAD_REQUEST.value(), "You must provide at least one show id."));
        }
        if (userService.addWatchedTvShows(userService.findById(userId), tvShowsIds) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RequestResponseDTO(HttpStatus.NOT_FOUND.value(), "Shows not found."));
        }
        User user = userService.findById(userId);
        user = userService.addWatchedTvShows(user,tvShowsIds);
        user = userService.update(user.getId(),user);
        return ResponseEntity.ok().body(userService.getUserWatchedTvShowsList(user));
    }
}
