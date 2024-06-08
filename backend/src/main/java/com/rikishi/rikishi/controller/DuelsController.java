package com.rikishi.rikishi.controller;

import com.rikishi.rikishi.model.Fight;
import com.rikishi.rikishi.model.User;
import com.rikishi.rikishi.model.WeightClass;
import com.rikishi.rikishi.model.entity.Duel;
import com.rikishi.rikishi.model.entity.Duels;
import com.rikishi.rikishi.provider.ConfigProvider;
import com.rikishi.rikishi.service.FightService;
import com.rikishi.rikishi.service.UserService;

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

    DuelsController(FightService fs, UserService us, ConfigProvider cp) {
        this.fightService = fs;
        this.userService = us;
        this.configProvider = cp;
    }

    @GetMapping("/duels")
    public Duels getDuels() {
        return new Duels(
            fightService.getFights().map(Fight::toJson).toList()
        );
    }

    @GetMapping("/duels/{id}")
    public Duel getDuel(@PathVariable Long id) {
        return fightService.getFightById(id).orElseThrow().toJson();
    }

    // can update winner only
    @PatchMapping("/duels/{id}")
    public void patchDuel(@RequestBody Map<String, String> fields, @PathVariable Long id) {
        Fight fight = fightService.getFightById(id).orElseThrow();

        Fight newFight = new Fight(
            fight.id(),
            fight.firstUser(),
            fight.secondUser(),
            fight.number(),
            fight.score1(),
            fight.score2(),
            fight.weightClass(),
            Long.parseLong(fields.getOrDefault("winnerId", String.valueOf(fight.winnerId())))
        );

        fightService.addFight(newFight);
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
