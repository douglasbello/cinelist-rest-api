package com.douglasbello.Cinelist.config;

import com.douglasbello.Cinelist.services.AdminService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final AdminService adminService;

    public SecurityConfig(AdminService adminService) {
        this.adminService = adminService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers(new AntPathRequestMatcher("/h2-console/**"))
                .permitAll()
                .requestMatchers(HttpMethod.GET, "/movies/**")
                .permitAll()
                .requestMatchers(HttpMethod.GET, "/tvshows/**")
                .permitAll()
                .requestMatchers(HttpMethod.GET, "/comments/**")
                .permitAll()
                .anyRequest().authenticated().and().cors();

        http.headers().frameOptions().disable();
        http.addFilterBefore(new Filter(adminService), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
