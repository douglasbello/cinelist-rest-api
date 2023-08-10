package com.douglasbello.Cinelist.controllers;

import com.douglasbello.Cinelist.dtos.user.LoginDTO;
import com.douglasbello.Cinelist.dtos.RequestResponseDTO;
import com.douglasbello.Cinelist.entities.User;
import com.douglasbello.Cinelist.entities.enums.UserRole;
import com.douglasbello.Cinelist.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admins")
public class AdminController {
    private final UserService userService;

    AdminController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<RequestResponseDTO> insert(@RequestBody LoginDTO dto) {
        User user = new User(dto.username(), dto.password(), UserRole.ADMIN);
        userService.signIn(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(new RequestResponseDTO(HttpStatus.CREATED.value(), "Admin created successfully!"));
    }
}
