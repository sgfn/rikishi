package com.rikishi.rikishi.controller;

import com.rikishi.rikishi.model.User;
import com.rikishi.rikishi.model.WeightClass;
import com.rikishi.rikishi.model.entity.Contestant;
import com.rikishi.rikishi.model.entity.Contestants;
import com.rikishi.rikishi.provider.ResConfigProvider;
import com.rikishi.rikishi.service.UsersService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ContestantsController {
    private final UsersService usersService;
    private final ResConfigProvider resConfigProvider;

    ContestantsController(UsersService us, ResConfigProvider rcp) {
        this.usersService = us;
        this.resConfigProvider = rcp;
    }

    @GetMapping("/contestants")
    public Contestants getContestants() {
        return new Contestants(
            usersService.getUsers().stream().map(user -> user.toJson()).toList()
        );
    }

    @GetMapping("/contestants/sorted/{userField}")
    public Contestants getSortedContestants(@PathVariable String userField) {
        return switch (userField) {
            case "age" -> new Contestants(
                usersService.getUsersSortedByAge().stream().map(user -> user.toJson()).toList()
            );
            case "name" -> new Contestants(
                usersService.getUsersSortedByName().stream().map(user -> user.toJson()).toList()
            );
            case "weight" -> new Contestants(
                usersService.getUsersSortedByWeight().stream().map(user -> user.toJson()).toList()
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

        usersService.addUser(User.fromJson(newContestant, resolvedWeightClass));
    }
}
