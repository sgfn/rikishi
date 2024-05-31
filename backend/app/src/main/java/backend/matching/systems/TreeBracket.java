package backend.matching.systems;

import backend.matching.Player;
import org.graalvm.collections.Pair;

import java.util.*;

public class TreeBracket implements MatchingSystem {
    private final List<Player> arrayTree = new ArrayList<>(15);
    private int actualMatch;
    private OctetRoundName currentRound;

    @Override
    public void nextMatch() {
        if (currentRound == OctetRoundName.FINAL)
            throw new RuntimeException("There is nothing afet finals");
        if (actualMatch + 1 < currentRound.getIndexBound()) actualMatch++;
        else {
            currentRound = currentRound.goUp();
            actualMatch = currentRound.getIndexStart();
        }
    }

    @Override
    public void chooseWinner(Player winner) {
        if (getCurrentPlayers().contains(winner)) arrayTree.set(actualMatch, winner);
        else throw new IllegalArgumentException("choosed winner do not play this match");
    }

    @Override
    public Set<Player> getCurrentPlayers() {
        Set<Player> players = new HashSet<>();
        if (arrayTree.isEmpty()) return players;
        if (arrayTree.size() <= (actualMatch + 1) * 2) throw new RuntimeException("Players are out of bound");
        players.add(arrayTree.get((actualMatch + 1) * 2));
        players.add(arrayTree.get((actualMatch + 1) * 2 + 1));
        return players;
    }

    @Override
    public void loadPlayers(Collection<Player> players) {
        for(int i = 0; i < players.size()*2 -1; i++) {
            arrayTree.add(null);
        }
        actualMatch = OctetRoundName.FIRST_FIGHT.getIndexStart();
        for (int i = 7; i < 15; i++) {
            arrayTree.set(i, players.iterator().next());
        }
    }
}
