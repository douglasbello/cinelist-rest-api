package com.douglasbello.Cinelist.dtos.user;

import com.douglasbello.Cinelist.entities.enums.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UserSignInDTO {
    @NotBlank(message = "Email cannot be blank.")
    @Email(message = "Email must be a valid email.")
    private String email;
    @Size(min = 4, max = 20, message = "Username size must be between 4 and 20 characters.")
    private String username;
    @Size(min = 8, max = 100, message = "Username size must be between 4 and 100 characters.")
    private String password;
    @NotNull(message = "Gender cannot be null.")
    private int gender;

    public UserSignInDTO(){}

    public UserSignInDTO(String email, String username, String password, int gender) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.gender = gender;
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
            return Gender.MALE;
        }
        return Gender.valueOf(gender);
    }

    public void setGender(int gender) {
        this.gender = gender;
    }
}