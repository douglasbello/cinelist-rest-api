package com.douglasbello.Cinelist.dtos;

import com.douglasbello.Cinelist.entities.Actor;
import com.douglasbello.Cinelist.entities.Director;
import com.douglasbello.Cinelist.entities.Genres;
import com.douglasbello.Cinelist.entities.Movie;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public class MovieDTO {
    private UUID id;
    private String title;
    private String overview;
    private String releaseYear;
    private Set<UUID> genresIds = new HashSet<>();
    private Set<UUID> directorsIds = new HashSet<>();
    private Set<Genres> genres = new HashSet<>();
    private Set<Director> directors = new HashSet<>();
    private Set<UUID> actorsIds = new HashSet<>();
    private Set<Actor> actors = new HashSet<>();

    public MovieDTO() {
    }

    public MovieDTO(UUID id, String title, String overview, String releaseYear, Set<UUID> genresIds, Set<UUID> directorsIds, Set<UUID> actorsIds) {
        this.id = id;
        this.title = title;
        this.overview = overview;
        this.releaseYear = releaseYear;
        this.genresIds = genresIds;
        this.directorsIds = directorsIds;
        this.actorsIds = actorsIds;
    }

    public MovieDTO(Movie movie) {
        this.id = movie.getId();
        this.title = movie.getTitle();
        this.overview = movie.getOverview();
        this.releaseYear = movie.getReleaseYear();
        this.directors = movie.getDirectors();
        this.genres = movie.getGenre();
        this.actors = movie.getActors();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    @Override
    public boolean equals(Object o) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;
        MovieDTO movieDTO = (MovieDTO) o;
        return Objects.equals(id, movieDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}