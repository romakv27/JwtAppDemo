package org.demo.jwtappdemo.service;

import org.demo.jwtappdemo.model.User;

import java.util.List;

public interface UserService {

    User register(User user);
    List<User> getAllUsers();
    User findByUsername(String username);
    User findUserById(Long id);
    void deleteUser(Long id);
}
