package com.douglasbello.Cinelist.repositories;

import com.douglasbello.Cinelist.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    User findUserByUsername(String username);

    User findUserByEmail(String email);
    
    UserDetails findByUsername(String username);
}
