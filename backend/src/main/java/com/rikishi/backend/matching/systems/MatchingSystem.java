package com.rikishi.backend.matching.systems;

import com.rikishi.backend.matching.Player;

import java.util.Collection;
import java.util.Set;

public interface MatchingSystem {
//    run in structure to the next sumo confrontation
    public void nextMatch();
//    allow to choose winner of match
    public void chooseWinner(Player winner);
//    return who will fight/ is fighting now
    public Set<Player> getCurrentPlayers();
//    allow to upload all players who will fight on contest
    public void loadPlayers(Collection<Player> players);
}
