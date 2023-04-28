package com.douglasbello.Cinelist.model.repositories;

import com.douglasbello.Cinelist.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}