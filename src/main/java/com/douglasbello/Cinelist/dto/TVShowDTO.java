package com.douglasbello.Cinelist.dto;

import com.douglasbello.Cinelist.entities.Director;
import com.douglasbello.Cinelist.entities.Genres;

import java.util.*;
import java.util.stream.Collectors;

public class TVShowDTO {
    private UUID id;
    private String title;
    private String overview;
    private String releaseYear;
    private List<GenresDTO> genres = new ArrayList<>();
    private Set<Director> directors = new HashSet<>();
    private Map<Integer, Integer> seasonsAndEpisodes = new HashMap<>();

    public TVShowDTO() {}

    public TVShowDTO(UUID id, String title, String overview, String releaseYear, Map<Integer, Integer> seasonsAndEpisodes) {
        this.id = id;
        this.title = title;
        this.overview = overview;
        this.releaseYear = releaseYear;
        this.seasonsAndEpisodes = seasonsAndEpisodes;
    }

    public TVShowDTO(UUID id, String title, String overview, String releaseYear, List<Genres> genres, Set<Director> directors, Map<Integer, Integer> seasonsAndEpisodes) {
        this.id = id;
        this.title = title;
        this.overview = overview;
        this.releaseYear = releaseYear;
        this.genres = genres.stream().map(GenresDTO::new).collect(Collectors.toList());
        this.directors = directors;
        this.seasonsAndEpisodes = seasonsAndEpisodes;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TVShowDTO tvShowDTO = (TVShowDTO) o;
        return Objects.equals(id, tvShowDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "TVShowDTO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", overview='" + overview + '\'' +
                ", releaseYear='" + releaseYear + '\'' +
                ", seasonsAndEpisodes=" + seasonsAndEpisodes +
                '}';
    }
}