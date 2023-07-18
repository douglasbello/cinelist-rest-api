package com.douglasbello.Cinelist.entities;

import com.douglasbello.Cinelist.entities.enums.Gender;
import com.douglasbello.Cinelist.entities.enums.UserRole;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
@Entity
@Table(name = "tb_users")
public class User implements UserDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	private String email;
	private String username;
	private String password;
	private int gender;
	private UserRole role;
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Comment> comments = new ArrayList<>();
	@ManyToMany
	@JoinTable(name = "tb_user_watched_movies", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "movie_id"))
	private Set<Movie> watchedMovies = new HashSet<>();
	@ManyToMany
	@JoinTable(name = "tb_user_watched_shows", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "tvshow_id"))
	private Set<TVShow> watchedTvShows = new HashSet<>();
	@ManyToMany
	@JoinTable(name = "tb_user_favorite_movies", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "movie_id"))
	private Set<Movie> favoriteMovies = new HashSet<>();
	@ManyToMany
	@JoinTable(name = "tb_user_favorite_shows", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "tvshow_id"))
	private Set<TVShow> favoriteTvShows = new HashSet<>();

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
	
	public User(String email, String username, String password, Gender gender, UserRole role) {
		this.email = email;
		this.username = username;
		this.password = password;
		setGender(gender.getCode());
		this.role = role;
	}

	public User(String email, String username, String password, int gender, UserRole role) {
		this.email = email;
		this.username = username;
		this.password = password;
		this.gender = gender;
		this.role = role;
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

	@Override
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
	
	public void setRole(UserRole role) {
		this.role = role;
	}
	
	public UserRole getRole() {
		return role;
	}

	public Set<Movie> getWatchedMovies() {
		return watchedMovies;
	}

	public Set<TVShow> getWatchedTvShows() {
		return watchedTvShows;
	}

	public Set<Movie> getFavoriteMovies() {
		return favoriteMovies;
	}

	public Set<TVShow> getFavoriteTvShows() {
		return favoriteTvShows;
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

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		if (this.role == UserRole.ADMIN) {
			return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
		}
		return List.of(new SimpleGrantedAuthority("ROLE_USER"));
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
}
