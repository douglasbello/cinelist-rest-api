package com.douglasbello.Cinelist.entities;

import java.util.Objects;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public class Rate {
	@ManyToOne
	@JoinColumn(name = "movie_id")
	private Movie movie;
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	private double rating;
	
	public Rate() {
		
	}
	
	public Rate(Movie movie, User user, double rating) {
		this.movie = movie;
		this.user = user;
		this.rating = rating;
	}

	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	@Override
	public int hashCode() {
		return Objects.hash(movie, rating, user);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Rate other = (Rate) obj;
		return Objects.equals(movie, other.movie)
				&& Double.doubleToLongBits(rating) == Double.doubleToLongBits(other.rating)
				&& Objects.equals(user, other.user);
	}

	@Override
	public String toString() {
		return "Rate [movie=" + movie + ", user=" + user + ", rating=" + rating + "]";
	}
}
