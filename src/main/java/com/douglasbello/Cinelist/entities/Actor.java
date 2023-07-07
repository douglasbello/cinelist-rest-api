package com.douglasbello.Cinelist.entities;

import com.douglasbello.Cinelist.entities.enums.Gender;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "tb_actors")
public class Actor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String name;
    private String birthDate;
    private int gender;
    @ManyToMany(mappedBy = "actors")
    @JsonIgnore
    private Set<Movie> movies = new HashSet<>();
    @ManyToMany(mappedBy = "actors")
    @JsonIgnore
    private Set<TVShow> tvShows = new HashSet<>();

    public Actor() {}

    public Actor(UUID id, String name, String birthDate, int gender) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.gender = gender;
    }

    public Actor(UUID id, String name, String birthDate, Gender gender) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
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

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
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