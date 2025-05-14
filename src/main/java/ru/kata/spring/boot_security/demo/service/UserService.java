package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.User;
import java.util.List;

public interface UserService {
    List<User> findAllUsers();

    void save(User user);

    void update(User user);

    User findById(Long id);

    void deleteById(Long id);

    User findByUsername(String username);
}