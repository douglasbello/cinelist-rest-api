package com.douglasbello.Cinelist.entities;

import com.douglasbello.Cinelist.entities.enums.Gender;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "actors")
public class Actor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String name;
    private LocalDate birthDate;
    private int gender;
    private int age;
    @ManyToMany(mappedBy = "actors")
    @JsonIgnore
    private Set<Movie> movies = new HashSet<>();
    @ManyToMany(mappedBy = "actors")
    @JsonIgnore
    private Set<TVShow> tvShows = new HashSet<>();

    public Actor() {
    }

    public Actor(UUID id, String name, LocalDate birthDate, int gender) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.gender = gender;
        setAge(this.birthDate);
    }

    public Actor(String name, int gender) {
        this.name = name;
    }

    public Actor(UUID id, String name, LocalDate birthDate, Gender gender) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.gender = gender.getCode();
        setAge(this.birthDate);
    }

    public Actor(UUID id, String name, Gender gender) {
        this.id = id;
        this.name = name;
        this.gender = gender.getCode();
    }

    @PrePersist
    public void generateUuid() {
        if (this.id == null)
            this.id = UUID.randomUUID();
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

    public LocalDate getBirthDate() {
        return birthDate;
    }

    /* this setter returns a boolean because is used in the controller to verify if the birthDate provided is in the correct format. */
    public boolean setBirthDate(String birthDate) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            this.birthDate = LocalDate.parse(birthDate, formatter);
            setAge(this.birthDate);
            return true;
        } catch (DateTimeParseException exception) {
            exception.printStackTrace();
            return false;
        }

    }

    public void setAge(LocalDate birthDate) {
        LocalDate currentDate = LocalDate.now();

        Period period = Period.between(birthDate, currentDate);
        this.age = period.getYears();
    }

    public int getAge() {
        return age;
    }

    public Gender getGender() {
        return Gender.valueOf(gender);
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public Set<Movie> getMovies() {
        return movies;
    }

    public Set<TVShow> getTvShows() {
        return tvShows;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Actor actor = (Actor) o;
        return Objects.equals(id, actor.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Actor{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", gender=" + gender +
                ", movies=" + movies +
                ", tvShows=" + tvShows +
                '}';
    }
}