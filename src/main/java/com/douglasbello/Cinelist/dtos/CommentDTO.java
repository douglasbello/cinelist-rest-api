package com.douglasbello.Cinelist.dtos;

import com.douglasbello.Cinelist.entities.Comment;

import java.util.Objects;
import java.util.UUID;

public class CommentDTO {
    private UUID userId;
    private UUID showOrMovieId;
    private String comment;

    public CommentDTO() {
    }

    public CommentDTO(UUID userId, UUID showOrMovieId, String comment) {
        this.userId = userId;
        this.showOrMovieId = showOrMovieId;
        this.comment = comment;
    }

    public CommentDTO(Comment comment) {
        this.userId = comment.getUser().getId();
        setShowOrMovieIdFromObj(comment);
        this.comment = comment.getComment();
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public UUID getShowOrMovieId() {
        return showOrMovieId;
    }

    public void setShowOrMovieIdFromObj(Comment comment) {
        if ( comment.getTvShow() == null ) {
            showOrMovieId = comment.getMovie().getId();
        }
        if ( comment.getMovie() == null ) {
            showOrMovieId = comment.getTvShow().getId();
        }
    }

    public void setShowOrMovieId(UUID showOrMovieId) {
        this.showOrMovieId = showOrMovieId;
    }

    public String getComment() {
        return comment;
    }

    public void setCommentText(String comment) {
        this.comment = comment;
    }

    @Override
    public boolean equals(Object o) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;
        CommentDTO that = (CommentDTO) o;
        return Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }
}
