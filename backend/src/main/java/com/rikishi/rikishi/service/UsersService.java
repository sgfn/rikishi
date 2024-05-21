package com.rikishi.rikishi.service;

import java.util.*;

import org.apache.logging.log4j.util.PropertySource;
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

    public List<User> getUsersSortedByAge() {
        List<User> usersList = new ArrayList<>(users.values().stream().toList());
        usersList.sort(Comparator.comparing(User::age));
        return usersList;
    }

    public List<User> getUsersSortedByName() {
        List<User> usersList = new ArrayList<>(users.values().stream().toList());
        usersList.sort(Comparator.comparing(User::name));
        return usersList;
    }

    public List<User> getUsersSortedByWeight() {
        List<User> usersList = new ArrayList<>(users.values().stream().toList());
        usersList.sort(Comparator.comparing(User::weight));
        return usersList;
    }

    public Optional<User> getUserById(long id) {
        return users.containsKey(id) ? Optional.of(users.get(id)) : Optional.empty();
    }
}
