package com.douglasbello.Cinelist.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.douglasbello.Cinelist.repositories.UserRepository;

public class AuthorizationService implements UserDetailsService {
	private final UserRepository repository;
	
	public AuthorizationService(UserRepository repository) {
		this.repository = repository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return repository.findByUsername(username);
	}
}