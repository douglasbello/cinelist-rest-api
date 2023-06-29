package com.douglasbello.Cinelist.dto;

import com.douglasbello.Cinelist.entities.Genres;

import java.util.UUID;

public class GenresDTO {
    private UUID id;
    private String genre;
    public GenresDTO() {
    }

    public GenresDTO(Genres genre) {
        this.id = genre.getId();
        this.genre = genre.getGenre();
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
}
