package com.douglasbello.Cinelist.controllers;

import com.douglasbello.Cinelist.dtos.LoginDTO;
import com.douglasbello.Cinelist.dtos.RequestResponseDTO;
import com.douglasbello.Cinelist.dtos.TokenDTO;
import com.douglasbello.Cinelist.dtos.UserDTO;
import com.douglasbello.Cinelist.entities.User;
import com.douglasbello.Cinelist.entities.enums.UserRole;
import com.douglasbello.Cinelist.security.TokenService;
import com.douglasbello.Cinelist.services.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping( value = "/users" )
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
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDTO userDTO = new UserDTO(userService.findByUsername(currentUser.getUsername()));
        return ResponseEntity.ok().body(userDTO);
    }

    @PostMapping( value = "/sign-in" )
    public ResponseEntity<?> signIn(@RequestBody UserDTO obj) {
        Object[] errors = userService.validateUserDto(obj);
        int errorCode = (int) errors[0];
        if ( errorCode != HttpStatus.OK.value() ) {
            return ResponseEntity.status(HttpStatus.valueOf(errorCode)).body(new RequestResponseDTO(errorCode, errors[1].toString()));
        }
        obj.setRole(UserRole.USER);
        userService.signIn(obj);
        return ResponseEntity.status(HttpStatus.CREATED).body(new RequestResponseDTO(HttpStatus.CREATED.value(), "Account created successfully!"));
    }

    @PostMapping( value = "/login" )
    public ResponseEntity<?> login(@RequestBody LoginDTO dto) {
        if ( dto.username() == null || dto.password() == null ) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RequestResponseDTO(HttpStatus.BAD_REQUEST.value(), "The username and password cannot be null."));
        }
        var usernamePassword = new UsernamePasswordAuthenticationToken(dto.username(), dto.password());
        var auth = authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok().body(new TokenDTO(token));
    }

    @GetMapping( value = "/watched-movies" )
    public ResponseEntity<?> getUserWatchedMovies() {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user;
        if ( userService.findByUsername(currentUser.getUsername()) == null ) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new RequestResponseDTO(HttpStatus.FORBIDDEN.value(), "User unauthorized."));
        }
        user = userService.findByUsername(currentUser.getUsername());
        return ResponseEntity.ok().body(userService.getUserWatchedMoviesList(user));
    }

    @GetMapping( value = "/favorite-movies" )
    public ResponseEntity<?> getUserFavoriteMovies() {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findByUsername(currentUser.getUsername());
        return ResponseEntity.ok().body(userService.getUserFavoriteMoviesList(user));
    }

    @PostMapping( value = "/watched-movies" )
    public ResponseEntity<?> addMoviesToWatchedList(@RequestBody Set<UUID> moviesId) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findByUsername(currentUser.getUsername());
        if ( moviesId.size() == 0 ) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RequestResponseDTO(HttpStatus.BAD_REQUEST.value(), "You need to pass at least one movie id."));
        }
        if ( userService.addWatchedMovies(user, moviesId) == null ) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RequestResponseDTO(HttpStatus.NOT_FOUND.value(), "Movies not found."));
        }

        user = userService.addWatchedMovies(user, moviesId);
        user = userService.update(user.getId(), user);
        return ResponseEntity.ok().body(userService.getUserWatchedMoviesList(user));
    }

    @PostMapping( value = "/favorite-movies" )
    public ResponseEntity<?> addMoviesToFavoriteList(@RequestBody Set<UUID> moviesId) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findByUsername(currentUser.getUsername());
        if ( moviesId.size() == 0 ) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RequestResponseDTO(HttpStatus.BAD_REQUEST.value(), "You need to pass at least one movie id."));
        }
        if ( userService.addFavoriteMovies(user, moviesId) == null ) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RequestResponseDTO(HttpStatus.NOT_FOUND.value(), "Movies not found."));
        }

        user = userService.addFavoriteMovies(user, moviesId);
        user = userService.update(user.getId(), user);
        return ResponseEntity.ok().body(userService.getUserFavoriteMoviesList(user));
    }

    @GetMapping( value = "/watched-shows" )
    public ResponseEntity<?> getUserWatchedShows() {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findByUsername(currentUser.getUsername());
        return ResponseEntity.ok().body(userService.getUserWatchedTvShowsList(user));
    }

    @GetMapping( value = "/favorite-shows" )
    public ResponseEntity<?> getUserFavoriteShows() {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findByUsername(currentUser.getUsername());
        return ResponseEntity.ok().body(userService.getUserFavoriteTvShowsList(user));
    }

    @PostMapping( value = "/watched-shows" )
    public ResponseEntity<?> addTvShowsToWatchedList(@RequestBody Set<UUID> tvShowsIds) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findByUsername(currentUser.getUsername());
        if ( tvShowsIds.size() == 0 ) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RequestResponseDTO(HttpStatus.BAD_REQUEST.value(), "You must provide at least one show id."));
        }
        if ( userService.addWatchedTvShows(user, tvShowsIds) == null ) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RequestResponseDTO(HttpStatus.NOT_FOUND.value(), "Shows not found."));
        }

        user = userService.addWatchedTvShows(user, tvShowsIds);
        user = userService.update(user.getId(), user);
        return ResponseEntity.ok().body(userService.getUserWatchedTvShowsList(user));
    }

    @PostMapping( value = "/favorite-shows" )
    public ResponseEntity<?> addTvShowsToFavoriteList(@RequestBody Set<UUID> tvShowsIds) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findByUsername(currentUser.getUsername());
        if ( tvShowsIds.size() == 0 ) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RequestResponseDTO(HttpStatus.BAD_REQUEST.value(), "You must provide at least one show id."));
        }
        if ( userService.addFavoriteTvShows(user, tvShowsIds) == null ) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RequestResponseDTO(HttpStatus.NOT_FOUND.value(), "Shows not found."));
        }

        user = userService.addFavoriteTvShows(user, tvShowsIds);
        user = userService.update(user.getId(), user);
        return ResponseEntity.ok().body(userService.getUserFavoriteTvShowsList(user));
    }
}
