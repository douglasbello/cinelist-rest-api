package com.douglasbello.Cinelist.dtos;

import com.douglasbello.Cinelist.entities.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class MovieDTOResponse {
    private UUID id;
    private String title;
    private String overview;
    private String releaseYear;
    private Set<Genres> genres = new HashSet<>();

    public MovieDTOResponse() {}

    public MovieDTOResponse(Movie movie) {
        this.id = movie.getId();
        this.title = movie.getTitle();
        this.overview = movie.getOverview();
        this.releaseYear = movie.getReleaseYear();
        this.genres = movie.getGenre();
    }

    public MovieDTOResponse(MovieDTO dto) {
        this.id = dto.getId();
        this.title = dto.getTitle();
        this.overview = dto.getOverview();
        this.releaseYear = dto.getReleaseYear();
        this.genres = dto.getGenres();
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

    public Set<Genres> getGenres() {
        return genres;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovieDTOResponse movieDTO = (MovieDTOResponse) o;
        return Objects.equals(id, movieDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}