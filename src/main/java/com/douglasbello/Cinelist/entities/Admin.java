package com.douglasbello.Cinelist.entities;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;

@Entity
@Table(name = "tb_admins")
public class Admin {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	private String name;
	private String password;
	private String token;

	public Admin() {
		setToken();
	}

	public Admin(String name, String password) {
		setToken();
		this.name = name;
		this.password = password;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getToken() {
		return token;
	}

	public void setToken() {
		Random rd = new Random();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 16; i++) {
			sb.append(rd.nextInt(10));
		}
		token = sb.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Admin admin = (Admin) o;
		return id == admin.id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public String toString() {
		return "Admin{" +
				"id=" + id +
				", name='" + name + '\'' +
				", password='" + password + '\'' +
				", token=" + token +
				'}';
	}
}
