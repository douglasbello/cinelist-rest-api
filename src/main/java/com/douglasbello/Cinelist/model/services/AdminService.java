package com.douglasbello.Cinelist.model.services;

import com.douglasbello.Cinelist.model.entities.Admin;
import com.douglasbello.Cinelist.model.repositories.AdminRepository;
import com.douglasbello.Cinelist.model.services.exceptions.DatabaseException;
import com.douglasbello.Cinelist.model.services.exceptions.ResourceNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AdminService {
    private final AdminRepository repository;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AdminService(AdminRepository repository) {
        this.repository = repository;
    }

    public List<Admin> findAll() {
        return repository.findAll();
    }

    public Admin findById(UUID id) {
        Optional<Admin> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public Admin insert(Admin admin) {
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        return repository.save(admin);
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
}