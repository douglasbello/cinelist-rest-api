package com.douglasbello.Cinelist.entities;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "tb_tvshows")
public class TVShow implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;

	private String title;

	private String overview;

	private String director;

	@OneToMany(mappedBy = "tvShow", cascade = CascadeType.ALL)
	private List<Comment> comments;
	private Integer seasons;

	private Integer episodes;

	@ElementCollection
	@CollectionTable(name = "seasons_episodes", joinColumns = @JoinColumn(name = "tv_show_id"))
	@MapKeyColumn(name = "season")
	@Column(name = "episodes")
	private Map<Integer, Integer> seasonsAndEpisodes = new HashMap<>();

	@ManyToMany
	@JoinTable(name = "tb_tvshow_genre", joinColumns = @JoinColumn(name = "tvshow_id"), inverseJoinColumns = @JoinColumn(name = "genres_id"))
	private List<Genres> genre = new ArrayList<>();

	private String releaseYear;

	public TVShow() {
		setSeasons();
		setEpisodes();
	}

	public TVShow(String title, String overview, String director, String releaseYear) {
		setSeasons();
		setEpisodes();
		this.title = title;
		this.overview = overview;
		this.director = director;
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

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public Integer getSeasons() {
		return seasons;
	}

	public Integer getEpisodes() {
		return episodes;
	}

	public void setSeasons() {
		seasons = seasonsAndEpisodes.size();
	}

	public void setEpisodes() {
		Integer sum = 0;
		for (Integer value : seasonsAndEpisodes.values()) {
			sum += value;
		}
		episodes = sum;
	}

	public Map<Integer, Integer> getSeasonsAndEpisodes() {
		return seasonsAndEpisodes;
	}

	public void putSeasonAndEpisodeAndUpdate(Integer season, Integer episodes) {
		seasonsAndEpisodes.put(season, episodes);
		setSeasons();
		setEpisodes();
	}

	public List<Comment> getComments() {
		return comments;
	}

	public List<Genres> getGenre() {
		return genre;
	}

	public String getReleaseYear() {
		return releaseYear;
	}

	public void setReleaseYear(String releaseYear) {
		this.releaseYear = releaseYear;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		TVShow tvShow = (TVShow) o;
		return id == tvShow.id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public String toString() {
		return "TVShow{" +
				"id=" + id +
				", title='" + title + '\'' +
				", overview='" + overview + '\'' +
				", comments=" + comments +
				", seasons=" + seasons +
				", episodes=" + episodes +
				", seasonsAndEpisodes=" + seasonsAndEpisodes +
				'}';
	}
}
