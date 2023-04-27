package com.douglasbello.Cinelist.services;

import com.douglasbello.Cinelist.model.entities.User;
import com.douglasbello.Cinelist.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public void save(User user) {
        repository.save(user);
    }

    public void saveAll(List<User> users) {
        repository.saveAll(users);
    }
}