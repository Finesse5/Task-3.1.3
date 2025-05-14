package ru.kata.spring.boot_security.demo.configs;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Set;

@Component
public class SuccessUserHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        String selectedRole = request.getParameter("role");
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

        if (selectedRole != null) {
            if (roles.contains(selectedRole)) {
                if (selectedRole.equals("ROLE_ADMIN")) {
                    response.sendRedirect("/admin");
                } else if (selectedRole.equals("ROLE_USER")) {
                    response.sendRedirect("/user");
                }
            } else {
                response.sendRedirect("/?error=role_mismatch");
            }
        } else {
            response.sendRedirect("/");
        }
    }
}