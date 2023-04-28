package com.douglasbello.Cinelist.services;

import com.douglasbello.Cinelist.model.entities.Admin;
import com.douglasbello.Cinelist.repositories.AdminRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
    private final AdminRepository repository;

    public AdminService(AdminRepository repository) {
        this.repository = repository;
    }

    public void save(Admin admin) {
        repository.save(admin);
    }

    public List<Admin> findAll() {
        return repository.findAll();
    }
}
