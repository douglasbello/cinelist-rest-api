package com.douglasbello.Cinelist.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "movie_id")
    @JsonIgnore
    private Movie movie;
    @ManyToOne
    @JoinColumn(name = "tv_show_id")
    @JsonIgnore
    private TVShow tvShow;
    private String comment;

    public Comment() {
    }

    public Comment(User user, Movie movie, String comment) {
        this.user = user;
        this.movie = movie;
        this.comment = comment;
    }

    public Comment(UUID id, User user, Movie movie, String comment) {
        this.id = id;
        this.user = user;
        this.movie = movie;
        this.comment = comment;
    }

    public Comment(User user, TVShow tvShow, String comment) {
        this.user = user;
        this.tvShow = tvShow;
        this.comment = comment;
    }

    @PrePersist
    public void generateUuid() {
        if (this.id == null)
            this.id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public TVShow getTvShow() {
        return tvShow;
    }

    public void setTvShow(TVShow tvShow) {
        this.tvShow = tvShow;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Comment comment = (Comment) o;
        return id == comment.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", user=" + user +
                ", movie=" + movie +
                ", comment='" + comment + '\'' +
                '}';
    }
}
