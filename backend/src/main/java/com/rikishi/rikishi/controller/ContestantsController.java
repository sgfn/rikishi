package com.rikishi.rikishi.controller;

import com.rikishi.rikishi.model.Sex;
import com.rikishi.rikishi.model.User;
import com.rikishi.rikishi.model.WeightClass;
import com.rikishi.rikishi.model.entity.Contestant;
import com.rikishi.rikishi.model.entity.Contestants;
import com.rikishi.rikishi.model.entity.CategoryCheckResult;
import com.rikishi.rikishi.provider.ResConfigProvider;
import com.rikishi.rikishi.service.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class ContestantsController {
    private final UserService userService;
    private final ResConfigProvider resConfigProvider;

    ContestantsController(UserService us, ResConfigProvider rcp) {
        this.userService = us;
        this.resConfigProvider = rcp;
    }

    @GetMapping("/contestants")
    public Contestants getContestants() {
        return new Contestants(
            userService.getUsers().map(User::toJson).toList()
        );
    }

    @GetMapping("/contestants/sorted/{userField}")
    public Contestants getSortedContestants(@PathVariable String userField) {
        return switch (userField) {
            case "age" -> new Contestants(
                userService.getUsersSortedByAge().map(User::toJson).toList()
            );
            case "name" -> new Contestants(
                userService.getUsersSortedByName().map(User::toJson).toList()
            );
            case "weight" -> new Contestants(
                userService.getUsersSortedByWeight().map(User::toJson).toList()
            );
            default -> throw new RuntimeException("contestants sort argument is not valid");
        };
    }

    @PutMapping("/contestants/{id}")
    public void putContestant(@RequestBody Contestant newContestant, @PathVariable Long id) {
        if (newContestant.id() != id)
            throw new RuntimeException("contestant id differs");

        WeightClass resolvedWeightClass =
            resConfigProvider.getWeightClassByName(newContestant.weightCategory()).orElseThrow();

        userService.addUser(User.fromJson(newContestant, resolvedWeightClass));
    }

    @PatchMapping("/contestants/{id}")
    public void patchContestant(@RequestBody Map<String, String> fields, @PathVariable Long id) {
        User user = userService.getUserById(id).orElseThrow();

        User newUser = new User(
            user.id(),
            fields.getOrDefault("name", user.name()),
            fields.getOrDefault("surname", user.surname()),
            Integer.parseInt(fields.getOrDefault("age", String.valueOf(user.age()))),
            Double.parseDouble(fields.getOrDefault("weight", String.valueOf(user.weight()))),
            resConfigProvider.getWeightClassByName(fields.getOrDefault("weightClass", user.weightClass().name())).orElseThrow(),
            Sex.fromString(fields.getOrDefault("sex", user.sex().toString())).orElseThrow(),
            fields.getOrDefault("country", user.country()),
            fields.getOrDefault("photoLink", user.photoLink())
        );

        userService.addUser(newUser);
    }

    @GetMapping("/contestants/{id}")
    public Contestant getContestant(@PathVariable Long id) {
        return userService.getUserById(id).orElseThrow().toJson();
    }

    @GetMapping("/contestants/{id}/validateCategory")
    public CategoryCheckResult validateContestantCategory(@PathVariable Long id) {
        return new CategoryCheckResult(
            userService.getUserById(id).orElseThrow().hasValidCategory()
        );
    }

    @GetMapping("/contestants/filter")
    public Contestants filterContestants(
        @RequestParam(required = false) Double minWeight,
        @RequestParam(required = false) Double maxWeight,
        @RequestParam(required = false) String sex,
        @RequestParam(required = false) Integer minAge,
        @RequestParam(required = false) Integer maxAge) {
        Stream<User> filteredUsers = userService.filterUsers(minWeight, maxWeight, sex, minAge, maxAge);
        return new Contestants(filteredUsers.map(User::toJson).collect(Collectors.toList()));
    }

    @PostMapping("/contestants/import-csv")
    public void importCSV(
        @RequestBody String path
    ) {
        try {
            userService.importFromFile(Path.of(path));
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
