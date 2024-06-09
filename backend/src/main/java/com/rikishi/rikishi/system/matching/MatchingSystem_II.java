package com.rikishi.rikishi.system.matching;

import com.rikishi.rikishi.model.Fight;
import com.rikishi.rikishi.model.User;

import java.util.Collection;
import java.util.List;


// ths is update of Matching System which work properly with FightService
public interface MatchingSystem_II {
    void loadPlayers(Collection<User> players);

    void updateFight(Fight fight);

    List<Fight> getAllFights();

    List<Fight> getCurrentFights();
}
