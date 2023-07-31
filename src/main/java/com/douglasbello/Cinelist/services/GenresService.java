package com.douglasbello.Cinelist.services;

import com.douglasbello.Cinelist.dtos.GenresDTO;
import com.douglasbello.Cinelist.dtos.MovieDTOResponse;
import com.douglasbello.Cinelist.dtos.TVShowDTOResponse;
import com.douglasbello.Cinelist.entities.Genres;
import com.douglasbello.Cinelist.repositories.GenresRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class GenresService {

    private final GenresRepository repository;

    public GenresService(GenresRepository repository) {
        this.repository = repository;
    }

    public List<GenresDTO> findAll() {
        List<Genres> result = repository.findAll();
        return result.stream().map(GenresDTO::new).toList();
    }

    public Genres findById(UUID id) {
        Optional<Genres> genres = repository.findById(id);
        return genres.orElse(null);
    }

    public GenresDTO insert(Genres genre) {
        Genres obj = repository.save(genre);
        return new GenresDTO(genre);
    }

    public void insertAll(List<Genres> genresList) {
        repository.saveAll(genresList);
    }

    public GenresDTO findByGenre(String name) {
        name = name.replace("-", " ");
        if ( repository.findByGenreContainingIgnoreCase(name) == null ) {
            return null;
        }
        return new GenresDTO(repository.findByGenreContainingIgnoreCase(name));
    }

    public Set<MovieDTOResponse> findMoviesByGenreId(UUID id) {
        if ( findById(id) == null ) {
            return Collections.emptySet();
        }
        Genres genre = findById(id);
        return genre.getMovie().stream().map(MovieDTOResponse::new).collect(Collectors.toSet());
    }

    public Set<MovieDTOResponse> findMoviesByGenreName(String name) {
        if ( findByGenre(name) == null ) {
            return Collections.emptySet();
        }
        name = name.replace("-", " ");
        Genres genres = repository.findByGenreContainingIgnoreCase(name);
        return genres.getMovie().stream().map(MovieDTOResponse::new).collect(Collectors.toSet());
    }

    public Set<TVShowDTOResponse> findShowsByGenreId(UUID id) {
        if ( findById(id) == null ) {
            return Collections.emptySet();
        }
        Genres genres = findById(id);
        return genres.getTvShow().stream().map(TVShowDTOResponse::new).collect(Collectors.toSet());
    }

    public Set<TVShowDTOResponse> findShowsByGenreName(String name) {
        if ( findByGenre(name) == null ) {
            return Collections.emptySet();
        }
        name = name.replace("-", " ");
        Genres genres = repository.findByGenreContainingIgnoreCase(name);
        return genres.getTvShow().stream().map(TVShowDTOResponse::new).collect(Collectors.toSet());
    }
}
