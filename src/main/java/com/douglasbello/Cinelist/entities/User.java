package com.douglasbello.Cinelist.entities;

import com.douglasbello.Cinelist.entities.enums.Gender;
import jakarta.persistence.*;

import java.util.*;
import java.util.stream.Collectors;
@Entity
@Table(name = "tb_users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	private String email;
	private String username;
	private String password;
	private int gender;
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Comment> comments = new ArrayList<>();
	@ManyToMany
	@JoinTable(name = "tb_user_watched_movies", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "movie_id"))
	private Set<Movie> watchedMovies = new HashSet<>();

	public User() {
	}

	public User(String email, String username, String password, Gender gender) {
		this.email = email;
		this.username = username;
		this.password = password;
		setGender(gender.getCode());
	}

	public User(String email, String username, String password, int gender) {
		this.email = email;
		this.username = username;
		this.password = password;
		this.gender = gender;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Comment> getComments() {
		return comments;
	}

    public Set<UUID> getCommentsIds() {
        Set<UUID> ids = comments.stream().map(Comment::getId).collect(Collectors.toSet());
        return ids;
    }

	public Gender getGender() {
		return Gender.valueOf(gender);
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public Set<Movie> getWatchedMovies() {
		return watchedMovies;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		User user = (User) o;
		return id == user.id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public String toString() {
		return "User{" +
				"id=" + id +
				", email='" + email + '\'' +
				", username='" + username + '\'' +
				", password='" + password + '\'' +
				", comments=" + comments +
				'}';
	}
}
