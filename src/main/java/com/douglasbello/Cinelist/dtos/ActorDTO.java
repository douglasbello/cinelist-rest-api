package com.douglasbello.Cinelist.dtos;

import com.douglasbello.Cinelist.entities.Actor;
import com.douglasbello.Cinelist.entities.enums.Gender;

import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.UUID;

public class ActorDTO {
    private UUID id;
    private String name;
    private String birthDate;
    private int age;
    private int gender;

    public ActorDTO() {}

    public ActorDTO(UUID id, String name, String birthDate, int gender) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.gender = gender;
    }

    public ActorDTO(Actor actor) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        this.id = actor.getId();
        this.name = actor.getName();
        this.birthDate = actor.getBirthDate().format(formatter);
        this.age = actor.getAge();
        this.gender = actor.getGender().getCode();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public Gender getGender() {
    	if (gender < 1 || gender > 3) {
    		return Gender.MALE;
    	}
        return Gender.valueOf(gender);
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActorDTO actorDTO = (ActorDTO) o;
        return Objects.equals(id, actorDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}