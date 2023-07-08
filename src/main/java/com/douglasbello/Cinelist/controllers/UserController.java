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
        if (obj.getEmail() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RequestResponseDTO(HttpStatus.BAD_REQUEST.value(), "Email cannot be blank."));
        }

        if (userService.checkIfTheEmailIsAlreadyInUse(obj.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new RequestResponseDTO(HttpStatus.CONFLICT.value(), "Email is already in use."));
        }

        if (obj.getUsername().contains(" ")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RequestResponseDTO(HttpStatus.BAD_REQUEST.value(), "The username cannot contain spaces."));
        }

        if (obj.getUsername().length() < 4 || obj.getUsername().length() > 16) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RequestResponseDTO(HttpStatus.BAD_REQUEST.value(), "Username cannot be less than 4 or bigger than 16."));
        }

        if (userService.checkIfTheUsernameIsAlreadyInUse(obj.getUsername())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new RequestResponseDTO(HttpStatus.CONFLICT.value(), "Username is already in use."));
        }

        if (obj.getPassword().length() < 8 || obj.getPassword().length() > 100) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RequestResponseDTO(HttpStatus.BAD_REQUEST.value(), "Password cannot be less than 8 or bigger than 100."));
        }

        if (obj.getGender().getCode() < 1 || obj.getGender().getCode() > 3) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RequestResponseDTO(HttpStatus.BAD_REQUEST.value(), "Gender code cannot be bigger than 3 or less than 1."));
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
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RequestResponseDTO(HttpStatus.BAD_REQUEST.value(),"You can only pass the email or username for login, not the two."));
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

    @PostMapping(value = "/{userId}/movies/add")
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

    @PostMapping(value = "/{userId}/shows/add")
    public ResponseEntity<?> addTvShowsToWatchedList(@PathVariable UUID userId, @RequestBody Set<UUID> tvShowsIds) {
        if (userService.findById(userId) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RequestResponseDTO(HttpStatus.NOT_FOUND.value(), "User not found."));
        }
        if (tvShowsIds.size() == 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RequestResponseDTO(HttpStatus.BAD_REQUEST.value(), "You need to pass at least one show id."));
        }
        if (userService.addWatchedTvShows(userService.findById(userId), tvShowsIds) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RequestResponseDTO(HttpStatus.NOT_FOUND.value(), "Shows not found."));
        }
        User user = userService.findById(userId);
        user = userService.addWatchedTvShows(user,tvShowsIds);
        user = userService.update(user.getId(),user);
        return ResponseEntity.ok().body(userService.getUserWatchedTvShowsList(user));
    }

    @GetMapping(value = "/{userId}/shows")
    public ResponseEntity<?> getUserWatchedShows(@PathVariable UUID userId) {
        if (userService.findById(userId) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RequestResponseDTO(HttpStatus.NOT_FOUND.value(), "User doesn't exists."));
        }
        return ResponseEntity.ok().body(userService.getUserWatchedTvShowsList(userService.findById(userId)));
    }
}
