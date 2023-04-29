package com.douglasbello.Cinelist.model.repositories;

import com.douglasbello.Cinelist.model.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CommentRepository extends JpaRepository<Comment, UUID> {
}