package com.douglasbello.Cinelist.dtos;

import java.io.Serial;
import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

import com.douglasbello.Cinelist.entities.User;
import com.douglasbello.Cinelist.entities.enums.Gender;

import java.util.HashSet;

public class UserDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private UUID id;
    private String email;
    private String username;
    private String password;
    /* here, the gender code is a string instead of an integer, but why?, because spring was increasing the number that I passed in post requests, for example, if I pass 1 in the
    request, my controller would receive the number 2, so i changed this attribute to string
     */
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
