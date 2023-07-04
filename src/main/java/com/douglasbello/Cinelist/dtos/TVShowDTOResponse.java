package com.douglasbello.Cinelist.dtos;

import com.douglasbello.Cinelist.entities.Director;
import com.douglasbello.Cinelist.entities.Genres;
import com.douglasbello.Cinelist.entities.TVShow;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.*;
import java.util.stream.Collectors;

public class TVShowDTOResponse {
    private UUID id;
    private String title;
    private String overview;
    private String releaseYear;
    private List<Genres> genres = new ArrayList<>();
    private Set<Director> directors = new HashSet<>();
    private Map<Integer, Integer> seasonsAndEpisodes = new HashMap<>();

    public TVShowDTOResponse() {}

    public TVShowDTOResponse(TVShow tvShow) {
        this.id = tvShow.getId();
        this.title = tvShow.getTitle();
        this.overview = tvShow.getTitle();
        this.releaseYear = tvShow.getReleaseYear();
        this.genres = tvShow.getGenre();
        this.directors = tvShow.getDirectors();
        this.seasonsAndEpisodes = tvShow.getSeasonsAndEpisodes();
    }

    public TVShowDTOResponse(TVShowDTO tvShowDTO) {
        this.id = tvShowDTO.getId();
        this.title = tvShowDTO.getTitle();
        this.overview = tvShowDTO.getTitle();
        this.releaseYear = tvShowDTO.getReleaseYear();
        this.genres = tvShowDTO.getGenres();
        this.directors = tvShowDTO.getDirectors();
        this.seasonsAndEpisodes = tvShowDTO.getSeasonsAndEpisodes();
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

    public Map<Integer, Integer> getSeasonsAndEpisodes() {
        return seasonsAndEpisodes;
    }

    public Set<Director> getDirectors() {
        return directors;
    }

    public List<Genres> getGenres() {
        return genres;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TVShowDTOResponse tvShowDTOResponse = (TVShowDTOResponse) o;
        return Objects.equals(id, tvShowDTOResponse.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}