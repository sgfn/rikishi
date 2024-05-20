package com.rikishi.rikishi.service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import com.rikishi.rikishi.loader.UserLoader;
import com.rikishi.rikishi.repository.UserRepository;
import org.springframework.stereotype.Service;

import com.rikishi.rikishi.model.User;

@Service("us")
public class UserService {
    private static Map<Long, User> users = new HashMap<>();
    private final UserRepository userRepository;
    private final UserLoader userLoader;

    public UserService(UserRepository userRepository, UserLoader userLoader) throws IOException {
        this.userRepository = userRepository;
        this.userLoader = userLoader;
    }

    public void addUser(User user) {
        userRepository.add(user);
    }

    public List<User> getUsers() {
        return userRepository.findAll().toList();
    }

    public Optional<User> getUserById(long id) {
        return userRepository.findById(id);
    }
}
