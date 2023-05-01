package com.douglasbello.Cinelist.model.config;

import com.douglasbello.Cinelist.model.entities.Admin;
import com.douglasbello.Cinelist.model.services.AdminService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.Collections;
import java.util.List;

@Configuration
public class InternToken {

    private final AdminService adminService;


    public InternToken(AdminService adminService) {
        this.adminService = adminService;
    }


    public Authentication decodeToken(HttpServletRequest request) {
        if (adminService.count() != 0) {
            List<Admin> admins = adminService.findAll();
            for (Admin admin : admins) {
                System.out.println(admin.getToken());
                if (request.getHeader("Authorization").equals("Bearer " + admin.getToken())) {
                    return new UsernamePasswordAuthenticationToken(admin.getName(), null, Collections.emptyList());
                }
            }
        }
        return null;
    }
}
