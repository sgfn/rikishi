package com.rikishi.rikishi.service;

import java.util.stream.Stream;
import java.util.*;

import com.rikishi.rikishi.model.User;
import com.rikishi.rikishi.model.WeightClass;
import com.rikishi.rikishi.provider.ResConfigProvider;
import com.rikishi.rikishi.repository.FightRepository;
import com.rikishi.rikishi.system.SystemPicker;
import com.rikishi.rikishi.system.matching.MatchingSystem_II;
import org.springframework.stereotype.Service;

import com.rikishi.rikishi.model.Fight;

@Service("ds")
public class FightService implements AutoCloseable {
    private final FightRepository fightRepository;
    private final Map<WeightClass, MatchingSystem_II> tournaments = new HashMap<>();
    private final ResConfigProvider resConfigProvider;
    private final UserService userService;


    public FightService(FightRepository fr, UserService us, ResConfigProvider rcf) {
        this.fightRepository = fr;
        this.resConfigProvider = rcf;
        this.userService = us;

        for (WeightClass weightClass : rcf.getWeightClasses()) {
            try {
                Collection<User> players = us.getUsers().filter(weightClass::isValid).toList();
                MatchingSystem_II matchingSystem = SystemPicker.pickSystem(players.size());
                matchingSystem.loadPlayers(players);
                fightRepository.addAll(matchingSystem.getAllFights());
                tournaments.put(weightClass, matchingSystem);
                System.out.println("\u001B[32m" + weightClass.name() + "\u001B[0m");
            } catch (IllegalArgumentException e) {
                System.err.println(weightClass.name() + "\t\t\t" + e.getMessage());
            }
        }
        ;
    }

    public void updateFight(Fight fight) {
        tournaments.get(fight.weightClass()).updateFight(fight);
        fightRepository.add(fight);
    }

    public void tryReload() {
        for (WeightClass weightClass : resConfigProvider.getWeightClasses()) {
            if (tournaments.containsKey(weightClass) && !tournaments.get(weightClass).playersLoaded()) {
                try {
                    Collection<User> players = userService.getUsers().filter(weightClass::isValid).toList();
                    MatchingSystem_II matchingSystem = SystemPicker.pickSystem(players.size());
                    matchingSystem.loadPlayers(players);
                    fightRepository.addAll(matchingSystem.getAllFights());
                    tournaments.put(weightClass, matchingSystem);
                    System.out.println("\u001B[32m" + weightClass.name() + " added\u001B[0m");
                } catch (IllegalArgumentException e) {
                    System.err.println(weightClass.name() + "\t\t\tunabled to reload");
                }
            }
        }
    }

    public Stream<Fight> getAllCategoryFights(WeightClass weightClass) {
        if (!tournaments.containsKey(weightClass))
            return Stream.empty();
        return tournaments.get(weightClass).getAllFights().stream();
    }

    public Stream<Fight> getCurrFights() {
        return tournaments.values().stream()
            .flatMap(system -> system.getCurrentFights().stream());
    }

    public Stream<Fight> getAllFights() {
        return tournaments.values().stream()
            .flatMap(system -> system.getAllFights().stream());
    }

    public Optional<Fight> getFightById(long id, WeightClass weightClass) {
        return fightRepository.findById((long) Objects.hash(id, weightClass));
    }

    @Override
    public void close() throws Exception {
        fightRepository.save();
    }
}
