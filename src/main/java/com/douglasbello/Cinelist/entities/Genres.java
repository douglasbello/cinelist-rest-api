package com.douglasbello.Cinelist.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "tb_genres")
public class Genres {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String genre;
    @JsonIgnore
    @ManyToMany(mappedBy = "genre")
    private List<Movie> movie = new ArrayList<>();
    @JsonIgnore
    @ManyToMany(mappedBy = "genres")
    private List<TVShow> tvShow = new ArrayList<>();

    public Genres() {
    }

    public Genres(UUID id, String genre) {
        this.id = id;
        this.genre = genre;
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

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public List<Movie> getMovie() {
        return movie;
    }

    public List<TVShow> getTvShow() {
        return tvShow;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Genres genres = (Genres) o;
        return id.equals(genres.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Genres{" +
                "id=" + id +
                ", genre='" + genre + '\'' +
                ", movie=" + movie +
                ", tvShow=" + tvShow +
                '}';
    }
}
