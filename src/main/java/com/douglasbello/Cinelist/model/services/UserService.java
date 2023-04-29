package com.douglasbello.Cinelist.model.services;

import com.douglasbello.Cinelist.model.entities.User;
import com.douglasbello.Cinelist.model.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
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
        return user.orElseThrow(RuntimeException::new);
    }

    public User insert(User user) {
        String password = passwordEncoder.encode(user.getPassword());
        user.setPassword(password);
        return repository.save(user);
    }

    public void delete(UUID id) {
        repository.deleteById(id);
    }

    public User update(UUID id,User obj) {
        User entity = repository.getReferenceById(id);
        updateData(entity,obj);
        return repository.save(entity);
    }

    private void updateData(User entity, User obj) {
        entity.setEmail(obj.getEmail());
        entity.setUsername(obj.getUsername());
    }
}