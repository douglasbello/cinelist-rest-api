package com.douglasbello.Cinelist.entities;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.*;

@Entity
@Table(name = "tb_movies")
public class Movie {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	private String title;
	private String overview;
	private String releaseYear;
	@ManyToMany
	@JoinTable(name = "tb_director_movie", joinColumns = @JoinColumn(name = "movie_id"), inverseJoinColumns = @JoinColumn(name = "director_id"))
	private Set<Director> directors = new HashSet<>();
	@ManyToMany
	@JoinTable(name = "tb_actor_movie", joinColumns = @JoinColumn(name = "movie_id"), inverseJoinColumns = @JoinColumn(name = "actor_id"))
	private Set<Actor> actors = new HashSet<>();
	@ManyToMany
	@JoinTable(name = "tb_movie_genre", joinColumns = @JoinColumn(name = "movie_id"), inverseJoinColumns = @JoinColumn(name = "genres_id"))
	private Set<Genres> genre = new HashSet<>();
	// users that
	@ManyToMany(mappedBy = "watchedMovies")
	@JsonIgnore
	private Set<User> users = new HashSet<>();
	@JsonIgnore
	@OneToMany(mappedBy = "movie")
	private Set<Comment> comments = new HashSet<>();

	public Movie() {
	}

	public Movie(String title, String overview, String releaseYear) {
		this.title = title;
		this.overview = overview;
		this.releaseYear = releaseYear;
	}

//	public Movie(UUID id, String title, String overview, String releaseYear, Set<Director> directors, Set<Genres> genre) {
//		this.id = id;
//		this.title = title;
//		this.overview = overview;
//		this.releaseYear = releaseYear;
//		this.directors = directors;
//		this.genre = genre;
//	}

	public Movie(UUID id, String title, String overview, String releaseYear, Set<Director> directors, Set<Genres> genre) {
		this.id = id;
		this.title = title;
		this.overview = overview;
		this.releaseYear = releaseYear;
		this.directors = directors;
		this.genre = genre;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getOverview() {
		return overview;
	}

	public void setOverview(String overview) {
		this.overview = overview;
	}

	public Set<Director> getDirectors() {
		return directors;
	}

	public Set<Comment> getComments() {
		return comments;
	}

	public String getReleaseYear() {
		return releaseYear;
	}

	public void setReleaseYear(String releaseYear) {
		this.releaseYear = releaseYear;
	}

	public Set<Genres> getGenre() {
		return genre;
	}

	public Set<Actor> getActors() {
		return actors;
	}

	public Set<User> getUsers() {
		return users;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Movie movie = (Movie) o;
		return id == movie.id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public String toString() {
		return "Movie{" +
				"id=" + id +
				", title='" + title + '\'' +
				", overview='" + overview + '\'' +
				", directors=" + directors +
				", genre=" + genre +
				", releaseYear='" + releaseYear + '\'' +
				", comments=" + comments +
				'}';
	}
}
