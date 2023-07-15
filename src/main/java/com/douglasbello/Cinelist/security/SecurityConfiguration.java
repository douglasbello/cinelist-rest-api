package com.douglasbello.Cinelist.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
	
	@Autowired
	private SecurityFilter securityFilter;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests()
                .requestMatchers(new AntPathRequestMatcher("/h2-console/**"))
                .permitAll()
				.requestMatchers(HttpMethod.POST, "/movies").hasRole("ADMIN")
				.requestMatchers(HttpMethod.POST, "/movies/**").hasRole("ADMIN")
				.requestMatchers(HttpMethod.POST, "/tvshows").hasRole("ADMIN")
				.requestMatchers(HttpMethod.POST, "tvshows/**").hasRole("ADMIN")
				.requestMatchers(HttpMethod.POST, "/directors").hasRole("ADMIN")
				.requestMatchers(HttpMethod.POST, "/genres").hasRole("ADMIN")
				.requestMatchers(HttpMethod.POST, "/actors").hasRole("ADMIN")
				.requestMatchers(HttpMethod.GET, "/users").hasRole("ADMIN")
				.requestMatchers(HttpMethod.DELETE, "/directors/**").hasRole("ADMIN")
				.requestMatchers(HttpMethod.POST, "/users/login")
				.permitAll()
				.requestMatchers(HttpMethod.POST, "/users/sign-in")
				.permitAll()
				.anyRequest().authenticated();
		
        http.headers(headers -> headers.frameOptions().disable());
        http.addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
				
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}