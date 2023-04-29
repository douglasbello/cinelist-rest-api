package com.douglasbello.Cinelist.model.services;

import com.douglasbello.Cinelist.model.entities.Admin;
import com.douglasbello.Cinelist.model.repositories.AdminRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AdminService {
    private final AdminRepository repository;

    public AdminService(AdminRepository repository) {
        this.repository = repository;
    }

    public List<Admin> findAll() {
        return repository.findAll();
    }

    public Admin findById(UUID id) {
        Optional<Admin> obj = repository.findById(id);
        return obj.orElseThrow(RuntimeException::new);
    }

    public Admin insert(Admin admin) {
        return repository.save(admin);
    }

    public void delete(UUID id) {
        repository.deleteById(id);
    }
}