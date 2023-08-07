package com.douglasbello.Cinelist.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.*;

@Entity
@Table(name = "shows")
public class TVShow {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String title;
    private String overview;
    private Integer seasons;
    private Integer episodes;
    private String releaseYear;
    @ManyToMany
    @JoinTable(name = "tb_director_tvshow", joinColumns = @JoinColumn(name = "tvshow_id"), inverseJoinColumns = @JoinColumn(name = "director_id"))
    private Set<Director> directors = new HashSet<>();
    @ManyToMany
    @JoinTable(name = "tb_actor_tvshow", joinColumns = @JoinColumn(name = "tvshow_id"), inverseJoinColumns = @JoinColumn(name = "actor_id"))
    private Set<Actor> actors = new HashSet<>();
    @OneToMany(mappedBy = "tvShow", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();
    @ElementCollection
    @CollectionTable(name = "seasons_episodes", joinColumns = @JoinColumn(name = "tv_show_id"))
    @MapKeyColumn(name = "season")
    @Column(name = "episodes")
    private Map<Integer, Integer> seasonsAndEpisodes = new HashMap<>();
    @ManyToMany
    @JoinTable(name = "tb_tvshow_genre", joinColumns = @JoinColumn(name = "tvshow_id"), inverseJoinColumns = @JoinColumn(name = "genres_id"))
    private List<Genres> genres = new ArrayList<>();
    @ManyToMany(mappedBy = "watchedTvShows")
    @JsonIgnore
    private Set<User> users = new HashSet<>();
    @ManyToMany(mappedBy = "favoriteTvShows")
    @JsonIgnore
    private Set<User> favoriteUsers = new HashSet<>();

    public TVShow() {
        setSeasons();
        setEpisodes();
    }

    public TVShow(String title, String overview, String releaseYear, Map<Integer, Integer> seasonsAndEpisodes) {
        this.title = title;
        this.overview = overview;
        this.releaseYear = releaseYear;
        this.seasonsAndEpisodes = seasonsAndEpisodes;
        setSeasons();
        setEpisodes();
    }

    public TVShow(String title, String overview, String releaseYear, Set<Director> directors, Map<Integer, Integer> seasonsAndEpisodes) {
        this.title = title;
        this.overview = overview;
        this.releaseYear = releaseYear;
        this.directors = directors;
        this.seasonsAndEpisodes = seasonsAndEpisodes;
        setSeasons();
        setEpisodes();
    }

    public TVShow(String title, String overview, String releaseYear, Set<Director> directors, Map<Integer, Integer> seasonsAndEpisodes, List<Genres> genres, Set<Actor> actors) {
        this.title = title;
        this.overview = overview;
        this.releaseYear = releaseYear;
        this.directors = directors;
        this.seasonsAndEpisodes = seasonsAndEpisodes;
        this.genres = genres;
        this.actors = actors;
        setSeasons();
        setEpisodes();
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

    public Integer getSeasons() {
        return seasons;
    }

    public Integer getEpisodes() {
        return episodes;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public List<Genres> getGenre() {
        return genres;
    }

    public String getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(String releaseYear) {
        this.releaseYear = releaseYear;
    }

    public Set<Actor> getActors() {
        return actors;
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

    public Set<User> getUsers() {
        return users;
    }

    public List<Genres> getGenres() {
        return genres;
    }

    public Set<User> getFavoriteUsers() {
        return favoriteUsers;
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
                ", director=" + directors +
                ", comments=" + comments +
                ", seasons=" + seasons +
                ", episodes=" + episodes +
                ", seasonsAndEpisodes=" + seasonsAndEpisodes +
                ", genre=" + genres +
                ", releaseYear='" + releaseYear + '\'' +
                '}';
    }
}
