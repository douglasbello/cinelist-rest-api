package com.douglasbello.Cinelist.dto;

import java.io.Serial;
import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

import com.douglasbello.Cinelist.entities.User;

import java.util.HashSet;

public class UserDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private UUID id;
    private String email;
    private String username;
    private String password;
    private Set<UUID> comments = new HashSet<>();

    public UserDTO() {
    }

    public UserDTO(User entity) {
        this.id = entity.getId();
        this.email = entity.getEmail();
        this.username = entity.getUsername();
        this.password = entity.getPassword();
        this.comments = entity.getCommentsIds();
    }

    public UserDTO(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
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

    @Override
    public String toString() {
        return "User{" +
               "id=" + id +
               ", email='" + email + '\'' +
               ", username='" + username + '\'' +
               ", password='" + password + '\'' +
               ", comments=" + comments +
               '}';
    }
}
