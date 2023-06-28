package com.douglasbello.Cinelist.controllers;

import com.douglasbello.Cinelist.dto.RequestResponseDTO;
import com.douglasbello.Cinelist.dto.UserDTO;
import com.douglasbello.Cinelist.entities.User;
import com.douglasbello.Cinelist.services.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/users")
public class UserController {
	private final UserService service;

	public UserController(UserService service) {
		this.service = service;
	}

	@GetMapping
	public ResponseEntity<List<User>> findAll() {
		return ResponseEntity.ok().body(service.findAll());
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<User> findById(@PathVariable UUID id) {
		return ResponseEntity.ok().body(service.findById(id));
	}

	@PostMapping(value = "/sign-in")
	public ResponseEntity<RequestResponseDTO> signIn(@RequestBody UserDTO obj) {

        if (obj.getUsername().length() < 4 || obj.getUsername().length() > 16) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RequestResponseDTO(400, "Username cannot be less than 4 or bigger than 16."));
        }

        if (obj.getPassword().length() < 8 || obj.getPassword().length() > 100) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RequestResponseDTO(400, "Password cannot be less than 8 or bigger than 100."));
        }

        if (service.checkIfTheUsernameIsAlreadyInUse(obj.getUsername())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new RequestResponseDTO(409, "Username is already in use."));
        }

        service.signIn(obj);
        return ResponseEntity.ok().body(new RequestResponseDTO(200, "Account created successfully!"));
	}

    @PostMapping(value = "/login")
    public ResponseEntity<RequestResponseDTO> login(@RequestBody UserDTO obj) {
        if (!service.login(obj.getEmail(), obj.getPassword())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new RequestResponseDTO(401, "Username or password incorrect"));
        }
        return ResponseEntity.ok().body(new RequestResponseDTO(200,"Login successfully."));
    }

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable UUID id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<User> update(@PathVariable UUID id, @RequestBody User obj) {
		obj = service.update(id, obj);
		return ResponseEntity.ok().body(obj);
	}
}
