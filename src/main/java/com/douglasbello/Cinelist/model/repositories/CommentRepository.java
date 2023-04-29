package com.douglasbello.Cinelist.model.repositories;

import com.douglasbello.Cinelist.model.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CommentRepository extends JpaRepository<Comment, UUID> {
    @Query("SELECT c FROM Comment c WHERE c.movie.id = :id")
    List<Comment> findAllCommentsByMovieId(UUID id);
    @Query("SELECT c FROM Comment c WHERE c.tvShow.id = :id")
    List<Comment> findAllCommentsByTvShowId(UUID id);
}