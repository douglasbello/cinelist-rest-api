package com.douglasbello.Cinelist.dtos;

import java.util.Set;
import java.util.UUID;

import com.douglasbello.Cinelist.entities.Movie;
import com.douglasbello.Cinelist.entities.User;
import com.douglasbello.Cinelist.entities.enums.Gender;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

import java.util.HashSet;

public class UserDTO {
    private UUID id;
    private String email;
    private String username;
    private String password;
    private int gender;

    public UserDTO() {
    }

    public UserDTO(String email, String username, String password, int gender) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.gender = gender;
    }

    public UserDTO(User entity) {
        this.id = entity.getId();
        this.email = entity.getEmail();
        this.username = entity.getUsername();
        this.password = entity.getPassword();
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

    public Gender getGender() {
    	if (gender < 1 || gender > 3) {
    		return Gender.OTHER;
    	}
        return Gender.valueOf(gender);
    }

    public void setGender(int gender) {
        this.gender = gender;
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
