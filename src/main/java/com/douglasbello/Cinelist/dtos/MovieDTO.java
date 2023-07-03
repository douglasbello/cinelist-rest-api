package com.douglasbello.Cinelist.dtos;

import com.douglasbello.Cinelist.entities.Comment;
import com.douglasbello.Cinelist.entities.Director;
import com.douglasbello.Cinelist.entities.Movie;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class MovieDTO {
    private UUID id;
    private String title;
    private String overview;
    private String releaseYear;
    private Set<GenresDTO> genres = new HashSet<>();
    private Set<DirectorDTO> directors = new HashSet<>();
    private Set<CommentDTO> comments = new HashSet<>();

    public MovieDTO() {}

    public MovieDTO(UUID id, String title, String overview, String releaseYear) {
        this.id = id;
        this.title = title;
        this.overview = overview;
        this.releaseYear = releaseYear;
    }

    public MovieDTO(Movie movie) {
        this.id = movie.getId();
        this.title = movie.getTitle();
        this.overview = movie.getOverview();
        this.releaseYear = movie.getReleaseYear();
        this.directors = movie.getDirectors().stream().map(DirectorDTO::new).collect(Collectors.toSet());
        this.genres = movie.getGenre().stream().map(GenresDTO::new).collect(Collectors.toSet());
        this.comments = movie.getComments().stream().map(CommentDTO::new).collect(Collectors.toSet());
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

    public Set<DirectorDTO> getDirectors() {
        return directors;
    }

    public Set<GenresDTO> getGenres() {
        return genres;
    }

    public Set<CommentDTO> getComments() {
        return comments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovieDTO movieDTO = (MovieDTO) o;
        return Objects.equals(id, movieDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}