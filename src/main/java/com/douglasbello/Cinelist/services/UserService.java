package com.douglasbello.Cinelist.services;

import com.douglasbello.Cinelist.dto.Mapper;
import com.douglasbello.Cinelist.entities.User;
import com.douglasbello.Cinelist.dto.UserDTO;
import com.douglasbello.Cinelist.entities.enums.Gender;
import com.douglasbello.Cinelist.repositories.UserRepository;
import com.douglasbello.Cinelist.services.exceptions.DatabaseException;
import com.douglasbello.Cinelist.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService<T> {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public List<User> findAll() {
        return repository.findAll();
    }

    public User findById(UUID id) {
        Optional<User> user = repository.findById(id);
        return user.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public User insert(User user) {
        String password = passwordEncoder.encode(user.getPassword());
        user.setPassword(password);
        return repository.save(user);
    }

    public User signIn(UserDTO dto) {
        User user = Mapper.userDtoToUser(dto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return repository.save(user);
    }

    public UserDTO login(UserDTO obj) {
        try {
            User entity;
            if (obj.getEmail() == null) {
                entity = repository.findUserByUsername(obj.getUsername());
            }
            else {
                entity = repository.findUserByEmail(obj.getEmail());
            }

            if (entity == null) {
                return null;
            }

            if (passwordEncoder.matches(obj.getPassword(), entity.getPassword())) {
                return new UserDTO(entity);
            }

        } catch (NoSuchElementException exception) {
            return null;
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

    public User update(UUID id,User obj) {
        try {
            User entity = repository.getReferenceById(id);
            updateData(entity,obj);
            return repository.save(entity);
        } catch (EntityNotFoundException exception) {
            throw new ResourceNotFoundException(id);
        }
    }

    private void updateData(User entity, User obj) {
        entity.setEmail(obj.getEmail());
        entity.setUsername(obj.getUsername());
    }

    public boolean checkIfTheUsernameIsAlreadyInUse(String username) {
        if (repository.findUserByUsername(username) == null) {
            return false;
        }
        return true;
    }

    public boolean checkIfTheEmailIsAlreadyInUse(String email) {
        if (repository.findUserByEmail(email) == null) {
            return false;
        }
        return true;
    }
}
