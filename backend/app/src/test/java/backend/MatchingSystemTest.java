package backend;

import backend.matching.Player;
import backend.matching.systems.MatchingSystem;
import backend.matching.systems.TreeBracket;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

public class MatchingSystemTest {
    @Test
    public void TreeBracketTest() {
        List<Player> players = List.of(
                new Player(), new Player(), new Player(), new Player(), new Player(), new Player(), new Player(), new Player()
        );
        Player player = players.get(0);
        MatchingSystem bracket = new TreeBracket();
        bracket.loadPlayers(players);

        Assertions.assertTrue(bracket.getCurrentPlayers().contains(player));
        Assertions.assertFalse(bracket.getCurrentPlayers().contains(new Player()));
    }

    @Test
    public void allPlayersTest() {
        List<Player> players = List.of(
                new Player(), new Player(), new Player(), new Player(), new Player(), new Player(), new Player(), new Player()
        );
        MatchingSystem bracket = new TreeBracket();
        bracket.loadPlayers(players);

        Set<Player> allPlayers = new HashSet<>();
        for (int i = 0; i < 4; i++){
            allPlayers.addAll(bracket.getCurrentPlayers());
            bracket.nextMatch();
        }

        Assertions.assertEquals( 8, allPlayers.size());
    }
}
