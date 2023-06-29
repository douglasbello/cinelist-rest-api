package com.douglasbello.Cinelist.entities;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "tb_movies")
public class Movie implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;
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
	@JoinTable(name = "tb_movie_genre", joinColumns = @JoinColumn(name = "movie_id"), inverseJoinColumns = @JoinColumn(name = "genres_id"))
	private List<Genres> genre = new ArrayList<>();
	@OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
	private List<Comment> comments = new ArrayList<>();

	public Movie() {
	}

	public Movie(String title, String overview, String releaseYear) {
		this.title = title;
		this.overview = overview;
		this.releaseYear = releaseYear;
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

	public List<Comment> getComments() {
		return comments;
	}

	public String getReleaseYear() {
		return releaseYear;
	}

	public void setReleaseYear(String releaseYear) {
		this.releaseYear = releaseYear;
	}

	public List<Genres> getGenre() {
		return genre;
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
