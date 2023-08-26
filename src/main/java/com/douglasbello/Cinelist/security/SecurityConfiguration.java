//package com.douglasbello.Cinelist.security;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfiguration {
//
//    @Autowired
//    private SecurityFilter securityFilter;
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.csrf(csrf -> csrf.disable())
//                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .authorizeHttpRequests()
//                .requestMatchers(new AntPathRequestMatcher("/h2-console/**"))
//                .permitAll()
//                .requestMatchers(HttpMethod.POST, "/api/admins").hasRole("ADMIN")
//                .requestMatchers(HttpMethod.POST, "/api/movies").hasRole("ADMIN")
//                .requestMatchers(HttpMethod.POST, "/apí/movies/{movieId}/actors").hasRole("ADMIN")
//                .requestMatchers(HttpMethod.POST, "/api/shows").hasRole("ADMIN")
//                .requestMatchers(HttpMethod.POST, "/api/shows/**").hasRole("ADMIN")
//                .requestMatchers(HttpMethod.POST, "/api/directors").hasRole("ADMIN")
//                .requestMatchers(HttpMethod.POST, "/api/genres").hasRole("ADMIN")
//                .requestMatchers(HttpMethod.POST, "/api/actors").hasRole("ADMIN")
//                .requestMatchers(HttpMethod.DELETE, "/api/directors/{id}").hasRole("ADMIN")
//                .requestMatchers(HttpMethod.DELETE, "/api/shows/{id}").hasRole("ADMIN")
//                .requestMatchers(HttpMethod.POST, "/api/users/login")
//                .permitAll()
//                .requestMatchers(HttpMethod.POST, "/api/users/sign-up")
//                .permitAll()
//                .anyRequest().authenticated();
//
//        http.headers(headers -> headers.frameOptions().disable());
//        http.addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class);
//        return http.build();
//
//    }
//
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
//        return authenticationConfiguration.getAuthenticationManager();
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//}
