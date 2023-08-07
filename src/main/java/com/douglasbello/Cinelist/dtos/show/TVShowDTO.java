package com.douglasbello.Cinelist.dtos.show;

import com.douglasbello.Cinelist.entities.Actor;
import com.douglasbello.Cinelist.entities.Director;
import com.douglasbello.Cinelist.entities.Genres;
import com.douglasbello.Cinelist.entities.TVShow;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.*;

public class TVShowDTO {
    @NotBlank(message = "Show title cannot be blank.")
    private String title;
    @NotBlank(message = "Show overview cannot be blank.")
    private String overview;
    @NotBlank(message = "Show release year cannot be blank.")
    private String releaseYear;
    @JsonIgnore
    private Set<Actor> actors = new HashSet<>();
    @NotEmpty(message = "The actorsIds list cannot be empty, you must provide at least one actor id.")
    private Set<UUID> actorsIds = new HashSet<>();
    @JsonIgnore
    private List<Genres> genres = new ArrayList<>();
    @NotEmpty(message = "The genresIds list cannot be empty, you must provide at least one genre id.")
    private Set<UUID> genresIds = new HashSet<>();
    @JsonIgnore
    private Set<Director> directors = new HashSet<>();
    @NotEmpty(message = "The directorsIds list cannot be empty, you must provide at least one director id.")
    private Set<UUID> directorsIds = new HashSet<>();
    @NotEmpty(message = "The seasonsAndEpisodes map cannot be empty, you must provide at least with one season with it's episodes.")
    private Map<Integer, Integer> seasonsAndEpisodes = new HashMap<>();

    public TVShowDTO() {
    }

    public TVShowDTO(String title, String overview, String releaseYear, Map<Integer, Integer> seasonsAndEpisodes) {
        this.title = title;
        this.overview = overview;
        this.releaseYear = releaseYear;
        this.seasonsAndEpisodes = seasonsAndEpisodes;
    }

    public TVShowDTO(String title, String overview, String releaseYear, Set<Actor> actors, List<Genres> genres, Set<Director> directors, Map<Integer, Integer> seasonsAndEpisodes) {
        this.title = title;
        this.overview = overview;
        this.releaseYear = releaseYear;
        this.actors = actors;
        this.genres = genres;
        this.directors = directors;
        this.seasonsAndEpisodes = seasonsAndEpisodes;
    }

    public TVShowDTO(String title, String overview, String releaseYear, Set<UUID> actorsIds, Set<UUID> genresIds, Map<Integer, Integer> seasonsAndEpisodes, Set<UUID> directorsIds) {
        this.title = title;
        this.overview = overview;
        this.releaseYear = releaseYear;
        this.actorsIds = actorsIds;
        this.genresIds = genresIds;
        this.seasonsAndEpisodes = seasonsAndEpisodes;
        this.directorsIds = directorsIds;
    }

    public TVShowDTO(TVShow obj) {
        this.title = obj.getTitle();
        this.overview = obj.getOverview();
        this.releaseYear = obj.getReleaseYear();
        this.actors = obj.getActors();
        this.genres = obj.getGenre();
        this.directors = obj.getDirectors();
        this.seasonsAndEpisodes = obj.getSeasonsAndEpisodes();
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

    public Map<Integer, Integer> getSeasonsAndEpisodes() {
        return seasonsAndEpisodes;
    }

    public Set<Director> getDirectors() {
        return directors;
    }

    public Set<UUID> getDirectorsIds() {
        return directorsIds;
    }

    public Set<UUID> getGenresIds() {
        return genresIds;
    }

    public List<Genres> getGenres() {
        return genres;
    }

    public Set<Actor> getActors() {
        return actors;
    }

    public Set<UUID> getActorsIds() {
        return actorsIds;
    }
}