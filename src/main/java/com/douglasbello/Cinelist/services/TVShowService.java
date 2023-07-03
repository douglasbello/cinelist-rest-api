package com.douglasbello.Cinelist.services;

import com.douglasbello.Cinelist.dtos.TVShowDTO;
import com.douglasbello.Cinelist.entities.TVShow;
import com.douglasbello.Cinelist.entities.User;
import com.douglasbello.Cinelist.repositories.TVShowRepository;
import com.douglasbello.Cinelist.services.exceptions.DatabaseException;
import com.douglasbello.Cinelist.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TVShowService {
    private final TVShowRepository repository;

    public TVShowService(TVShowRepository repository) {
        this.repository = repository;
    }

    public List<TVShowDTO> findAll() {
        List<TVShow> shows = repository.findAll();
        return shows.stream().map(TVShowDTO::new).collect(Collectors.toList());
    }

    public TVShow findById(UUID id) {
        Optional<TVShow> tvShow = repository.findById(id);
        return tvShow.orElse(null);
    }

    public TVShowDTO findByTitle(String title) {
        title = title.replace("-", " ");
        TVShowDTO dto = new TVShowDTO(repository.findByTitleContainingIgnoreCase(title));
        return dto;
    }

    public TVShowDTO insert(TVShow tvShow) {
        return new TVShowDTO(repository.save(tvShow));
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
}
