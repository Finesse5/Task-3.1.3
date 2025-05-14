package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.HttpSession;

@Controller
public class AuthController {

    @GetMapping("/")
    public String showRoleSelectionPage() {
        return "role-select";
    }

    @GetMapping("/login")
    public String showLoginPage(@RequestParam(required = false) String role, HttpSession session) {
        if (role != null) {
            session.setAttribute("selectedRole", role);
        }
        return "login";
    }
} 