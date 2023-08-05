package com.douglasbello.Cinelist.controllers;

import com.douglasbello.Cinelist.dtos.LoginDTO;
import com.douglasbello.Cinelist.dtos.RequestResponseDTO;
import com.douglasbello.Cinelist.dtos.UserDTO;
import com.douglasbello.Cinelist.entities.enums.UserRole;
import com.douglasbello.Cinelist.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping( "/admins" )
public class AdminController {
    private final UserService userService;

    AdminController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<RequestResponseDTO> insert(@RequestBody LoginDTO dto) {
        if ( userService.findByUsername(dto.username()) != null ) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RequestResponseDTO(HttpStatus.BAD_REQUEST.value(), "Username already in use."));
        }
        if ( dto.username() == null || dto.password() == null ) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RequestResponseDTO(HttpStatus.BAD_REQUEST.value(), "Username and password cannot be null."));
        }
        UserDTO user = new UserDTO();
        user.setUsername(dto.username());
        user.setPassword(dto.password());
        user.setRole(UserRole.ADMIN);
//        userService.signIn(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(new RequestResponseDTO(HttpStatus.CREATED.value(), "Admin created successfully!"));
    }
}