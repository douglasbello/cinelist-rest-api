package com.douglasbello.Cinelist.dtos;

import com.douglasbello.Cinelist.entities.Genres;
import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public class GenresDTO {
    private UUID id;
    @NotBlank(message = "The genre name cannot be null.")
    private String genre;

    public GenresDTO() {
    }

    public GenresDTO(UUID id) {
        this.id = id;
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
