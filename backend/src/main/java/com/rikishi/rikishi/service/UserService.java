package com.rikishi.rikishi.service;

import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.Stream;
import java.util.*;

import com.rikishi.rikishi.loader.UserLoader;
import com.rikishi.rikishi.repository.UserRepository;
import org.springframework.stereotype.Service;

import com.rikishi.rikishi.model.User;

@Service("us")
public class UserService implements AutoCloseable {
    private final UserRepository userRepository;
    private final UserLoader userLoader;

    public UserService(UserRepository userRepository, UserLoader userLoader) throws IOException {
        this.userRepository = userRepository;
        this.userLoader = userLoader;

        userRepository.load();
    }

    public void addUser(User user) {
        userRepository.add(user);
    }

    public Stream<User> getUsers() {
        return userRepository.findAll();
    }

    public Stream<User> getUsers(String sort) {
        if (sort == null)
            return getUsers();

        return switch (sort) {
            case "age" -> userRepository.findAll().sorted(Comparator.comparing(User::age));
            case "name" -> userRepository.findAll().sorted(Comparator.comparing(User::name));
            case "weight" -> userRepository.findAll().sorted(Comparator.comparing(User::weight));
            default -> getUsers();
        };
    }

    public Optional<User> getUserById(long id) {
        return userRepository.findById(id);
    }

    public Stream<User> sortFilterUsers(String sort, Double minWeight, Double maxWeight, String sex, Integer minAge, Integer maxAge) {
        return getUsers(sort)
            .filter(user -> minWeight == null || user.weight() >= minWeight)
            .filter(user -> maxWeight == null || user.weight() <= maxWeight)
            .filter(user -> sex == null || user.sex().toString().equalsIgnoreCase(sex))
            .filter(user -> minAge == null || user.age() >= minAge)
            .filter(user -> maxAge == null || user.age() <= maxAge);
    }


    public void importFromFile(Path path) throws IOException {
        userRepository.removeAll();
        userRepository.addAll(userLoader.load(path));
    }

    @Override
    public void close() throws Exception {
        userRepository.save();
    }
}
