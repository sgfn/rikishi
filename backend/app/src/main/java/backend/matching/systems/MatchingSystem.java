package backend.matching.systems;

import backend.matching.Player;
import org.graalvm.collections.Pair;

import java.util.Collection;

public interface MatchingSystem {
//    run in structure to the next sumo confrontation
    public void nextMatch();
//    allow to choose winner of match
    public void chooseWinner(Player winner);
//    return who will fight/ is fighting now
    public Pair<Player, Player> getCurrentPlayers();
//    allow to upload all players who will fight on contest
    public void loadPlayers(Collection<Player> players);
}
