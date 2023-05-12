package com.douglasbello.Cinelist.model.dto;

import com.douglasbello.Cinelist.model.entities.User;

import java.util.UUID;

public class UserDTO {
    private UUID id;
    private String email;
    private String username;
    private String password;

    public UserDTO(User entity) {
        id = entity.getId();
        email = entity.getEmail();
        username = entity.getUsername();
        password = entity.getPassword();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}