package com.douglasbello.Cinelist.services;

import com.douglasbello.Cinelist.dtos.GenresDTO;
import com.douglasbello.Cinelist.dtos.Mapper;
import com.douglasbello.Cinelist.dtos.TVShowDTO;
import com.douglasbello.Cinelist.entities.*;
import com.douglasbello.Cinelist.repositories.TVShowRepository;
import com.douglasbello.Cinelist.services.exceptions.DatabaseException;
import com.douglasbello.Cinelist.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TVShowService {
    private final TVShowRepository repository;
    private final DirectorService directorService;
    private final GenresService genresService;

    public TVShowService(TVShowRepository repository, DirectorService directorService, GenresService genresService) {
        this.repository = repository;
        this.directorService = directorService;
        this.genresService = genresService;
    }

    public List<TVShowDTO> findAll() {
        List<TVShow> shows = repository.findAll();
        return shows.stream().map(TVShowDTO::new).collect(Collectors.toList());
    }

    public TVShow findById(UUID id) {
        Optional<TVShow> tvShow = repository.findById(id);
        return tvShow.orElse(null);
    }

    public List<TVShowDTO> findByTitle(String title) {
        title = title.replace("-", " ");
        if (repository.findByTitleContainingIgnoreCase(title)  != null) {
            List<TVShowDTO> dto = repository.findByTitleContainingIgnoreCase(title).stream().map(TVShowDTO::new).collect(Collectors.toList());
            return dto;
        }
        return null;
    }

    public TVShowDTO insert(TVShow tvShow) {
        return new TVShowDTO(repository.save(tvShow));
    }

    public Set<TVShowDTO> findTvShowsByDirectorId(UUID id) {
        if (directorService.findById(id) != null) {
            Director director = directorService.findById(id);
            return director.getTvShows().stream().map(TVShowDTO::new).collect(Collectors.toSet());
        }
        return null;
    }

    public void delete(UUID id) {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException emptyResultDataAccessException) {
            throw new ResourceNotFoundException(id);
        } catch (DataIntegrityViolationException dataIntegrityViolationException) {
            throw new DatabaseException(dataIntegrityViolationException.getMessage());
        }
    }

    public TVShowDTO update(UUID id, TVShow obj) {
        try {
            TVShow entity = repository.getReferenceById(id);
            updateData(entity,obj);
            return new TVShowDTO(repository.save(entity));
        } catch (EntityNotFoundException exception) {
            throw new ResourceNotFoundException(id);
        }
    }

    private void updateData(TVShow entity, TVShow obj) {
        entity.setTitle(obj.getTitle());
        entity.setOverview(obj.getOverview());
    }

    public TVShowDTO getDirectorsAndGenres(TVShowDTO tvShowDTO) {
        // this if is verifying if both the directorIds and genresIds will return empty collections, if so, the method just return the dto
        if (tvShowDTO.getDirectorsIds().stream().map(directorService::findById).collect(Collectors.toSet()).size() == 0 ||
                tvShowDTO.getGenresIds().stream().map(genresService::findById).collect(Collectors.toList()).size() == 0) {
            return tvShowDTO;
        }
        for (UUID directorIds : tvShowDTO.getDirectorsIds()) {
            if (directorService.findById(directorIds) != null) {
                tvShowDTO.getDirectors().add(directorService.findById(directorIds));
            }
        }
        for (UUID genresIds : tvShowDTO.getGenresIds()) {
            if (genresService.findById(genresIds) != null) {
                tvShowDTO.getGenres().add(genresService.findById(genresIds));
            }
        }
        return tvShowDTO;
    }
}
