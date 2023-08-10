package com.douglasbello.Cinelist.controllers;

import com.douglasbello.Cinelist.dtos.*;
import com.douglasbello.Cinelist.dtos.Mapper;
import com.douglasbello.Cinelist.dtos.user.LoginDTO;
import com.douglasbello.Cinelist.dtos.user.UserDTO;
import com.douglasbello.Cinelist.dtos.user.UserSignInDTO;
import com.douglasbello.Cinelist.entities.User;
import com.douglasbello.Cinelist.security.TokenService;
import com.douglasbello.Cinelist.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping(value = "/users")
public class UserController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public UserController(UserService userService, AuthenticationManager authenticationManager, TokenService tokenService) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @GetMapping
    public ResponseEntity<UserDTO> getCurrentUser() {
        User currentUser = userService.getCurrentUser();
        UserDTO userDTO = new UserDTO(currentUser);
        return ResponseEntity.ok().body(userDTO);
    }

    @PostMapping(value = "/sign-in")
    public ResponseEntity<?> signIn(@Valid @RequestBody UserSignInDTO dto) {
        if (userService.findByEmail(dto.getEmail()) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new RequestResponseDTO(HttpStatus.CONFLICT.value(), "Email already in use."));
        }
        if (userService.findByUsername(dto.getUsername()) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new RequestResponseDTO(HttpStatus.CONFLICT.value(), "Username already in use."));
        }
        if (dto.getGender().getCode() < 1 || dto.getGender().getCode() > 3) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RequestResponseDTO(HttpStatus.BAD_REQUEST.value(), "Gender code cannot be less than 1 or bigger than 3. The gender codes are: MALE(1), FEMALE(2), PREFER NOT SAY(3)"));
        }
        UserDTO response = new UserDTO(userService.signIn(Mapper.dtoToUser(dto)));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDTO dto) {
        if (dto.username() == null || dto.password() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RequestResponseDTO(HttpStatus.BAD_REQUEST.value(), "The username and password cannot be null."));
        }
        var usernamePassword = new UsernamePasswordAuthenticationToken(dto.username(), dto.password());
        var auth = authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok().body(new TokenDTO(token));
    }

    @GetMapping(value = "/watched-movies")
    public ResponseEntity<?> getUserWatchedMovies() {
        User user = userService.getCurrentUser();
        return ResponseEntity.ok().body(userService.getUserWatchedMoviesList(user));
    }

    @GetMapping(value = "/favorite-movies")
    public ResponseEntity<?> getUserFavoriteMovies() {
        User user = userService.getCurrentUser();
        return ResponseEntity.ok().body(userService.getUserFavoriteMoviesList(user));
    }

    @PostMapping(value = "/watched-movies")
    public ResponseEntity<?> addMoviesToWatchedList(@RequestBody Set<UUID> moviesId) {
        User user = userService.getCurrentUser();
        if (userService.addWatchedMovies(user, moviesId) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RequestResponseDTO(HttpStatus.NOT_FOUND.value(), "Movies not found."));
        }

        user = userService.addWatchedMovies(user, moviesId);
        user = userService.update(user.getId(), user);
        return ResponseEntity.ok().body(userService.getUserWatchedMoviesList(user));
    }

    @PostMapping(value = "/favorite-movies")
    public ResponseEntity<?> addMoviesToFavoriteList(@RequestBody Set<UUID> moviesId) {
        User user = userService.getCurrentUser();
        if (userService.addFavoriteMovies(user, moviesId) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RequestResponseDTO(HttpStatus.NOT_FOUND.value(), "Movies not found."));
        }

        user = userService.addFavoriteMovies(user, moviesId);
        user = userService.update(user.getId(), user);
        return ResponseEntity.ok().body(userService.getUserFavoriteMoviesList(user));
    }

    @GetMapping(value = "/watched-shows")
    public ResponseEntity<?> getUserWatchedShows() {
        User user = userService.getCurrentUser();
        return ResponseEntity.ok().body(userService.getUserWatchedTvShowsList(user));
    }

    @GetMapping(value = "/favorite-shows")
    public ResponseEntity<?> getUserFavoriteShows() {
        User user = userService.getCurrentUser();
        return ResponseEntity.ok().body(userService.getUserFavoriteTvShowsList(user));
    }

    @PostMapping(value = "/watched-shows")
    public ResponseEntity<?> addTvShowsToWatchedList(@RequestBody Set<UUID> tvShowsIds) {
        User user = userService.getCurrentUser();
        if (userService.addWatchedTvShows(user, tvShowsIds) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RequestResponseDTO(HttpStatus.NOT_FOUND.value(), "Shows not found."));
        }

        user = userService.addWatchedTvShows(user, tvShowsIds);
        user = userService.update(user.getId(), user);
        return ResponseEntity.ok().body(userService.getUserWatchedTvShowsList(user));
    }

    @PostMapping(value = "/favorite-shows")
    public ResponseEntity<?> addTvShowsToFavoriteList(@RequestBody Set<UUID> tvShowsIds) {
        User user = userService.getCurrentUser();
        if (userService.addFavoriteTvShows(user, tvShowsIds) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RequestResponseDTO(HttpStatus.NOT_FOUND.value(), "Shows not found."));
        }

        user = userService.addFavoriteTvShows(user, tvShowsIds);
        user = userService.update(user.getId(), user);
        return ResponseEntity.ok().body(userService.getUserFavoriteTvShowsList(user));
    }
}
