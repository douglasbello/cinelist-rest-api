package com.douglasbello.Cinelist.dtos;

import jakarta.validation.constraints.NotBlank;

public class DirectorInputDTO {
    @NotBlank(message = "Director name cannot be blank.")
    private String name;
    @NotBlank(message = "Birth date cannot be blank.")
    private String birthDate;
    private int gender;

    public DirectorInputDTO(){}

    public DirectorInputDTO(String name, String birthDate, int gender) {
        this.name = name;
        this.birthDate = birthDate;
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }
}
