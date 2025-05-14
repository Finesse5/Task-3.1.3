package ru.kata.spring.boot_security.demo.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import jakarta.annotation.PostConstruct;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class DataInitializer {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public DataInitializer(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostConstruct
    public void init() {
        Role adminRole = roleService.findByName("ROLE_ADMIN");
        Role userRole = roleService.findByName("ROLE_USER");

        if (adminRole == null) {
            adminRole = new Role("ROLE_ADMIN");
            roleService.save(adminRole);
        }

        if (userRole == null) {
            userRole = new Role("ROLE_USER");
            roleService.save(userRole);
        }

        if (userService.findByUsername("admin") == null) {
            Set<Role> adminRoles = new HashSet<>();
            adminRoles.add(adminRole);

            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword("admin");
            admin.setEmail("admin@mail.ru");
            admin.setName("Admin");
            admin.setRoles(adminRoles);
            userService.save(admin);
        }

        if (userService.findByUsername("user") == null) {
            Set<Role> userRoles = new HashSet<>();
            userRoles.add(userRole);

            User user = new User();
            user.setUsername("user");
            user.setPassword("user");
            user.setEmail("user@mail.ru");
            user.setName("User");
            user.setRoles(userRoles);
            userService.save(user);
        }

        List<User> users = userService.findAllUsers();
        for (User user : users) {
            Set<Role> roles = new HashSet<>();
            if (user.getUsername().equals("admin")) {
                roles.add(adminRole);
            } else {
                roles.add(userRole);
            }
            user.setRoles(roles);
            userService.update(user);
        }
    }
} 