package com.rikishi.rikishi.controller;

import com.rikishi.rikishi.model.User;
import com.rikishi.rikishi.model.WeightClass;
import com.rikishi.rikishi.model.entity.Contestant;
import com.rikishi.rikishi.model.entity.Contestants;
import com.rikishi.rikishi.provider.ResConfigProvider;
import com.rikishi.rikishi.service.UserService;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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

    @GetMapping("/contestants/filter")
    public Contestants filterContestants(
        @RequestParam(required = false) Double minWeight,
        @RequestParam(required = false) Double maxWeight,
        @RequestParam(required = false) String sex,
        @RequestParam(required = false) Integer minAge,
        @RequestParam(required = false) Integer maxAge) {
        List<User> filteredUsers = usersService.filterUsers(minWeight, maxWeight, sex, minAge, maxAge);
        return new Contestants(filteredUsers.stream().map(User::toJson).collect(Collectors.toList()));
    }
}
