package com.rikishi.rikishi.system.matching;

import com.rikishi.rikishi.model.User;

import java.util.Collection;
import java.util.Set;

public interface MatchingSystem {
    //    run in structure to the next sumo confrontation
    public void nextMatch();

    //    allow to choose winner of match
    public void chooseWinner(User winner);

    //    return who will fight/ is fighting now
    public Set<User> getCurrentPlayers();

    //    allow to upload all players who will fight on contest
    public void loadPlayers(Collection<User> players);
}
