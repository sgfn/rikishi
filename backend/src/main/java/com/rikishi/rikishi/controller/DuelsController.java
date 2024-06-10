package com.rikishi.rikishi.controller;

import com.rikishi.rikishi.model.Fight;
import com.rikishi.rikishi.model.User;
import com.rikishi.rikishi.model.WeightClass;
import com.rikishi.rikishi.model.entity.Duel;
import com.rikishi.rikishi.model.entity.Duels;
import com.rikishi.rikishi.provider.ConfigProvider;
import com.rikishi.rikishi.provider.ResConfigProvider;
import com.rikishi.rikishi.service.FightService;
import com.rikishi.rikishi.service.UserService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class DuelsController {
    private final FightService fightService;
    private final UserService userService;
    private final ConfigProvider configProvider;
    private final ResConfigProvider resConfigProvider;

    DuelsController(FightService fs, UserService us, ConfigProvider cp, ResConfigProvider rcp) {
        this.fightService = fs;
        this.userService = us;
        this.configProvider = cp;
        this.resConfigProvider = rcp;
    }

    @GetMapping("/duels/{weightCategory}")
    public Duels getCategoryDuels(@PathVariable String weightCategory) {
        return new Duels(
            fightService.getAllCategoryFights(
                resConfigProvider.getWeightClassByName(weightCategory).orElseThrow()).map(Fight::toJson).toList()
        );
    }

    @GetMapping("/duels/{weightCategory}/{id}")
    public Duel getDuel(@PathVariable Long id, @PathVariable String weightCategory) {

        return fightService.getFightById(id, resConfigProvider.getWeightClassByName(weightCategory).orElseThrow())
            .orElseThrow().toJson();
    }

    // can update winner only
    @PatchMapping("/duels/{weightCategory}/{id}")
    public void patchDuel(@RequestBody Map<String, String> fields, @PathVariable Long id, @PathVariable String weightCategory) {
//        do zmiany
        Fight fight = fightService.getFightById(id, resConfigProvider.getWeightClassByName(weightCategory).orElseThrow())
            .orElseThrow();

        Fight newFight = new Fight(
            fight.id(),
            fight.firstUser(),
            fight.secondUser(),
            fight.number(),
            Integer.parseInt(fields.getOrDefault("score1", "0")),
            Integer.parseInt(fields.getOrDefault("score2", "0")),
            fight.weightClass(),
            Long.parseLong(fields.getOrDefault("winnerId", String.valueOf(fight.winnerId())))
        );

        fightService.updateFight(newFight);
    }

    @GetMapping("/duels/curr")
    public Duels getCurrentDuels() {
        Duels duels = new Duels(new ArrayList<>());
        duels.add(new Duel(0, "abc", 1, "xyz", 0, 0,0 ,1, "a", 0));
        duels.add(new Duel(0, "abc", 1, "xyz", 0, 0,0 ,1, "a", 0));
        duels.add(new Duel(0, "abc", 1, "xyz", 0, 0,0 ,1, "a", 0));
        return duels;
//        fightService.tryReload();
//        return new Duels(
//            fightService.getCurrFights().map(Fight::toJson).toList()
//        );
    }

    @GetMapping("/duels")
    public Duels getDuels() {
        return new Duels(
            fightService.getAllFights().map(Fight::toJson).toList()
        );
    }

    @PostMapping("/duels/generateLadder")
    public void generateLadder(@RequestBody Map<String, String> fields) {
        WeightClass wc = configProvider.getWeightClassByName(fields.get("weightCategory")).orElseThrow();
        Optional<User> firstBracketUser = userService.getUserById(
            // XXX: this assumes there's no user with id=-1; yes, it's an anti-pattern
            Long.parseLong(fields.getOrDefault("firstBracketContestant", "-1"))
        );
        Optional<User> secondBracketUser = userService.getUserById(
            Long.parseLong(fields.getOrDefault("secondBracketContestant", "-1"))
        );

        // TODO: implement logic to generate the ladder
    }
}
