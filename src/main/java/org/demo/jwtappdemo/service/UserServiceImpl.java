package org.demo.jwtappdemo.service;

import lombok.extern.slf4j.Slf4j;
import org.demo.jwtappdemo.model.Role;
import org.demo.jwtappdemo.model.Status;
import org.demo.jwtappdemo.model.User;
import org.demo.jwtappdemo.repository.RoleRepository;
import org.demo.jwtappdemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User register(User user) {
        Role roleUser = roleRepository.findByName("ROLE_USER");
        List<Role> userRoles = new ArrayList<>();
        userRoles.add(roleUser);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(userRoles);
        user.setStatus(Status.ACTIVE);

        User registeredUser = userRepository.save(user);

        log.info("IN register - user: {} successfully registered", registeredUser);

        return registeredUser;
    }

    @Override
    public List<User> getAllUsers() {
        List<User> result = userRepository.findAll();
        log.info("In getAllUsers - {} users found", result);
        return result;
    }

    @Override
    public User findByUsername(String username) {
        User result = userRepository.findByUsername(username);
        log.info("In findByUsername - user: {} found by username: {}", result, username);
        return result;
    }

    @Override
    public User findUserById(Long id) {
        User result = userRepository.findById(id).orElse(null);
        if(result == null){
            log.warn("In findUserById - no user found by id: {}", id);
        }
        log.info("In findUserById - user: {} found by id: {}", result);
        return null;
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
        log.info("In deleteUser - user with id: {} success deleted");
    }
}
