package backend;

import backend.matching.Player;
import backend.matching.systems.AllVsAllManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AllVsAllTest {
    private AllVsAllManager manager;
    private Player player1;
    private Player player2;
    private Player player3;
    private Player player4;

    @BeforeEach
    void setUp() {
        manager = new AllVsAllManager();
        player1 = new Player();
        player2 = new Player();
        player3 = new Player();
        player4 = new Player();
    }

    @Test
    public void testNextMatch() {
        List<Player> players = List.of(player1, player2, player3, player4);
        manager.loadPlayers(players);

        Assertions.assertTrue(manager.getCurrentPlayers().contains(player1));
        Assertions.assertFalse(manager.getCurrentPlayers().contains(new Player()));
        manager.nextMatch();

//        We don't have final score yet, so we can't go to next match
        Assertions.assertTrue(manager.getCurrentPlayers().contains(player2));
        Assertions.assertFalse(manager.getCurrentPlayers().contains(player3));
    }

    @Test
    public void testChooseWinner() {
        List<Player> players = List.of(player1, player2, player3, player4);
        manager.loadPlayers(players);
        manager.chooseWinner(player2);
        manager.chooseWinner(player1);
        manager.chooseWinner(player2);
        assertEquals(List.of(1, 2), manager.getMatchingSystem().getConcreteResult(0, 1));
        assertEquals(List.of(2, 1), manager.getMatchingSystem().getConcreteResult(1, 0));

    }

    @Test
    public void testChooseWinnerWithNextMach() {
        List<Player> players = List.of(player1, player2, player3, player4);
        manager.loadPlayers(players);

        Assertions.assertTrue(manager.getCurrentPlayers().contains(player1));
        Assertions.assertFalse(manager.getCurrentPlayers().contains(new Player()));
        manager.chooseWinner(player1);
        manager.chooseWinner(player1);
        manager.nextMatch();

        Assertions.assertTrue(manager.getCurrentPlayers().contains(player3));
        Assertions.assertFalse(manager.getCurrentPlayers().contains(player1));
    }

    @Test
    void testLoadPlayersWithInvalidNumberOfPlayers() {
        List<Player> players = List.of(player1, player2);
        assertThrows(IllegalArgumentException.class, () -> manager.loadPlayers(players));
    }


    @Test
    void testHandleTournamentEnd() {
//        Situation when two players have the same number of wins
        List<Player> players = List.of(player1, player2, player3, player4);

        manager.loadPlayers(players);

        simulateMatch(manager, player1, player2, player1); // 1 wins against 2
        simulateMatch(manager, player3, player2, player3); // 3 wins against 2
        simulateMatch(manager, player3, player4, player4); // 4 wins against 3

        simulateMatch(manager, player1, player3, player3); // 3 wins against 1
        simulateMatch(manager, player2, player4, player2); // 2 wins against 4

        simulateMatch(manager, player1, player4, player1); // 1 wins against 4

//        Extra part
        simulateMatch(manager, player1, player3, player3); // 3 wins against 1


        var finalStadings = manager.getFinalStandings();
        var firstPlayer = finalStadings.entrySet().iterator().next();
        assertEquals(firstPlayer.getKey(), player3);
        assertEquals(firstPlayer.getValue(), 3);
    }

    private static void simulateMatch(AllVsAllManager manager, Player player1, Player player2, Player winner) {
        Set<Player> currentPlayers = manager.getCurrentPlayers();
        if (currentPlayers.contains(player1) && currentPlayers.contains(player2)) {
            for (int i = 0; i < 2; i++) {
                manager.chooseWinner(winner);
            }
        } else {
            System.out.println("Error");
        }
        manager.nextMatch();
    }
}