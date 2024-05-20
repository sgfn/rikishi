package com.rikishi.rikishi.controller;

import com.rikishi.rikishi.model.User;
import com.rikishi.rikishi.model.WeightClass;
import com.rikishi.rikishi.model.entity.Contestant;
import com.rikishi.rikishi.model.entity.Contestants;
import com.rikishi.rikishi.provider.ResConfigProvider;
import com.rikishi.rikishi.service.UserService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
            userService.getUsers().stream().map(user -> user.toJson()).toList()
        );
    }

    @PutMapping("/contestants/{id}")
    public void putContestant(@RequestBody Contestant newContestant, @PathVariable Long id) {
        if (newContestant.id() != id)
            throw new RuntimeException("contestant id differs");

        WeightClass resolvedWeightClass =
            resConfigProvider.getWeightClassByName(newContestant.weightCategory()).orElseThrow();

        userService.addUser(User.fromJson(newContestant, resolvedWeightClass));
    }
}
