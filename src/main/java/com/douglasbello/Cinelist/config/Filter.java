package com.douglasbello.Cinelist.config;

import com.douglasbello.Cinelist.dto.RequestErrorDTO;
import com.douglasbello.Cinelist.services.AdminService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Configuration
public class Filter extends OncePerRequestFilter {

    private final AdminService adminService;

    public Filter(AdminService adminService) {
        this.adminService = adminService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // verifies if there is a "Authorization" in the header
        if (request.getHeader("Authorization") != null) {
            // will return a Authentication if the token in the Authorization header exists
            InternToken internToken = new InternToken(adminService);
            Authentication auth = internToken.decodeToken(request);

            if (auth != null) {
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
            else {
                RequestErrorDTO errorDTO = new RequestErrorDTO(401,"User not authorized for this system.");
                response.setStatus(errorDTO.getStatus());
                response.setContentType("application/json");
                ObjectMapper mapper = new ObjectMapper();
                response.getWriter().print(mapper.writeValueAsString(errorDTO));
                response.getWriter().flush();
                return;
            }
        }

        filterChain.doFilter(request,response);
    }
}
