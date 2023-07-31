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
@Table( name = "tb_directors" )
public class Director {
    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    private UUID id;
    private String name;
    private LocalDate birthDate;
    private int age;
    private int gender;
    @ManyToMany( mappedBy = "directors" )
    @JsonIgnore
    private Set<Movie> movies = new HashSet<>();
    @ManyToMany( mappedBy = "directors" )
    @JsonIgnore
    private Set<TVShow> tvShows = new HashSet<>();

    public Director() {
    }

    public Director(UUID id, String name, LocalDate birthDate) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        setAge(this.birthDate);
    }

    public Director(UUID id, String name, int gender) {
        this.id = id;
        this.name = name;
        this.gender = gender;
    }

    public Director(UUID id, String name, LocalDate birthDate, Gender gender) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.gender = gender.getCode();
        setAge(this.birthDate);
    }

    @PrePersist
    public void generateUuid() {
        if ( this.id == null )
            this.id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public Set<Movie> getMovies() {
        return movies;
    }

    public Set<TVShow> getTvShows() {
        return tvShows;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    /* this set returns a boolean because is used in the controller to verify if the birthDate provided is in the correct format. */
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

    @Override
    public boolean equals(Object o) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;
        Director director = (Director) o;
        return Objects.equals(id, director.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Director{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", movies=" + movies +
                ", tvShows=" + tvShows +
                '}';
    }
}