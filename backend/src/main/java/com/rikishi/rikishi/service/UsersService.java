package com.rikishi.rikishi.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.rikishi.rikishi.model.User;

@Service("us")
public class UsersService {
    private static Map<Long, User> users = new HashMap<>();

    public void addUser(User user) {
        users.put(user.id(), user);
    }

    public List<User> getUsers() {
        return users.values().stream().toList();
    }

    public Optional<User> getUserById(long id) {
        return users.containsKey(id) ? Optional.of(users.get(id)) : Optional.empty();
    }
}
