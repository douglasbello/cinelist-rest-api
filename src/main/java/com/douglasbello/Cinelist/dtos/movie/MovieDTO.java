package com.douglasbello.Cinelist.dtos.movie;

import com.douglasbello.Cinelist.entities.Actor;
import com.douglasbello.Cinelist.entities.Director;
import com.douglasbello.Cinelist.entities.Genres;
import com.douglasbello.Cinelist.entities.Movie;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class MovieDTO {
    @NotBlank(message = "The movie title cannot be blank.")
    private String title;
    @NotBlank(message = "The movie overview cannot be blank.")
    private String overview;
    @NotBlank(message = "You must provide the release year.")
    private String releaseYear;
    private Set<UUID> genresIds = new HashSet<>();
    private Set<UUID> directorsIds = new HashSet<>();
    @JsonIgnore
    private Set<Genres> genres = new HashSet<>();
    @JsonIgnore
    private Set<Director> directors = new HashSet<>();
    private Set<UUID> actorsIds = new HashSet<>();
    @JsonIgnore
    private Set<Actor> actors = new HashSet<>();

    public MovieDTO() {
    }

    public MovieDTO(String title, String overview, String releaseYear, Set<UUID> genresIds, Set<UUID> directorsIds, Set<UUID> actorsIds) {
        this.title = title;
        this.overview = overview;
        this.releaseYear = releaseYear;
        this.genresIds = genresIds;
        this.directorsIds = directorsIds;
        this.actorsIds = actorsIds;
    }

    public MovieDTO(Movie movie) {
        this.title = movie.getTitle();
        this.overview = movie.getOverview();
        this.releaseYear = movie.getReleaseYear();
        this.directors = movie.getDirectors();
        this.genres = movie.getGenre();
        this.actors = movie.getActors();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(String releaseYear) {
        this.releaseYear = releaseYear;
    }

    public Set<Director> getDirectors() {
        return directors;
    }

    public Set<Genres> getGenres() {
        return genres;
    }

    public Set<UUID> getGenresIds() {
        return genresIds;
    }

    public Set<UUID> getDirectorsIds() {
        return directorsIds;
    }

    public Set<UUID> getActorsIds() {
        return actorsIds;
    }

    public Set<Actor> getActors() {
        return actors;
    }
}