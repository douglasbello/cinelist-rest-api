package com.douglasbello.Cinelist.dtos;

import com.douglasbello.Cinelist.entities.Actor;
import com.douglasbello.Cinelist.entities.Director;
import com.douglasbello.Cinelist.entities.Genres;
import com.douglasbello.Cinelist.entities.TVShow;

import java.util.*;

public class TVShowDTO {
    private String title;
    private String overview;
    private String releaseYear;
    private Set<Actor> actors = new HashSet<>();
    private Set<UUID> actorsIds = new HashSet<>();
    private List<Genres> genres = new ArrayList<>();
    private Set<UUID> genresIds = new HashSet<>();
    private Set<Director> directors = new HashSet<>();
    private Set<UUID> directorsIds = new HashSet<>();
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