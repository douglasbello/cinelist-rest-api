package com.douglasbello.Cinelist.model.dto;

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

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public UUID getShowOrMovieId() {
        return showOrMovieId;
    }

    public void setShowOrMovieId(UUID showOrMovieId) {
        this.showOrMovieId = showOrMovieId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}