package com.douglasbello.Cinelist.dtos;

import java.util.UUID;

import com.douglasbello.Cinelist.entities.User;
import com.douglasbello.Cinelist.entities.enums.UserRole;

public class AdminDTO {
    private UUID id;
    private String email;
    private String username;
    private String password;
    private UserRole role;
    private int gender;

    public AdminDTO() {
    }

    public AdminDTO(String email, String username, String password, int gender) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.gender = gender;
    }

    public AdminDTO(User entity) {
        this.id = entity.getId();
        this.email = entity.getEmail();
        this.username = entity.getUsername();
        this.password = entity.getPassword();
        this.role = entity.getRole();
        this.gender = entity.getGender().getCode();
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

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
