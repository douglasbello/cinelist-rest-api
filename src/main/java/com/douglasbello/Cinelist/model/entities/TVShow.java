package com.douglasbello.Cinelist.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Entity
@Table(name = "tv_shows")
public class TVShow implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;
    private String overview;

    @JsonIgnore
    @OneToMany(mappedBy = "tvShow", cascade = CascadeType.ALL)
    private List<Comment> comments;

    @ElementCollection
    @CollectionTable(name = "seasons_episodes", joinColumns = @JoinColumn(name = "tv_show_id"))
    @MapKeyColumn(name = "seasons")
    @Column(name = "episodes")
    private Map<Integer,Integer> seasonsAndEpisodes = new HashMap<>();

    private Integer seasons;

    private Integer episodes;

    public TVShow() {
        setSeasons();
        setEpisodes();
    }

    public TVShow(String title, String overview) {
        setSeasons();
        setEpisodes();
        this.title = title;
        this.overview = overview;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public List<Comment> getComments() {
        return comments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
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
                ", seasonsAndEpisodes=" + seasonsAndEpisodes +
                ", seasons=" + seasons +
                ", episodes=" + episodes +
                '}';
    }
}
