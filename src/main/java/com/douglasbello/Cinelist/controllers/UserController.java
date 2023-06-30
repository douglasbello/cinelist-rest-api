package com.douglasbello.Cinelist.controllers;

import com.douglasbello.Cinelist.dto.RequestResponseDTO;
import com.douglasbello.Cinelist.dto.UserDTO;
import com.douglasbello.Cinelist.entities.enums.Gender;
import com.douglasbello.Cinelist.services.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/users")
public class UserController {
	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping(value = "/sign-in")
	public ResponseEntity<?> signIn(@RequestBody UserDTO obj) {
        System.out.println(obj.getGender());

        if (obj.getEmail() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RequestResponseDTO(400, "Email cannot be blank."));
        }

        if (userService.checkIfTheEmailIsAlreadyInUse(obj.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new RequestResponseDTO(409, "Email is already in use."));
        }

        if (obj.getUsername().length() < 4 || obj.getUsername().length() > 16) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RequestResponseDTO(400, "Username cannot be less than 4 or bigger than 16."));
        }

        if (userService.checkIfTheUsernameIsAlreadyInUse(obj.getUsername())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new RequestResponseDTO(409, "Username is already in use."));
        }

        if (obj.getPassword().length() < 8 || obj.getPassword().length() > 100) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RequestResponseDTO(400, "Password cannot be less than 8 or bigger than 100."));
        }

        if (obj.getGender() < 1 || obj.getGender() > 3) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RequestResponseDTO(400, "Gender code cannot be bigger than 3 or less than 1."));
        }

        userService.signIn(obj);
        return ResponseEntity.ok().body(new RequestResponseDTO(200, "Account created successfully!"));
	}

    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@RequestBody UserDTO obj) {
        if (obj.getEmail() != null && obj.getUsername() != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RequestResponseDTO(400,"You can only pass the email or username for login, not the two."));
        }
        if (userService.login(obj) == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new RequestResponseDTO(401, "Username or password incorrect"));
        }
        return ResponseEntity.ok().body(userService.login(obj));
    }
}
