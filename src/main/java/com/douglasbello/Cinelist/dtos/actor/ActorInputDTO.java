package com.douglasbello.Cinelist.dtos.actor;

import jakarta.validation.constraints.NotBlank;

public class ActorInputDTO {
    @NotBlank(message = "Actor name cannot be blank.")
    private String name;
    @NotBlank(message = "Birth date cannot be blank.")
    private String birthDate;
    private int gender;

    public ActorInputDTO(){}

    public ActorInputDTO(String name, String birthDate, int gender) {
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