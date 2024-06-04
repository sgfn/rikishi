package com.rikishi.rikishi.system;

import com.rikishi.rikishi.model.User;
import com.rikishi.rikishi.system.MatchingSystem;
import com.rikishi.rikishi.system.TreeBracket;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

// TODO: rewrite to utilise the User class

// class MatchingSystemTest {
//     @Test
//     void TreeBracketTest() {
//         List<User> players = List.of(
//             // new Player(), new Player(), new Player(), new Player(), new Player(), new Player(), new Player(), new Player()
//         );
//         MatchingSystem bracket = new TreeBracket();
//         bracket.loadPlayers(players);

//         Assertions.assertFalse(bracket.getCurrentPlayers().contains(new Player()));
//     }

//     @Test
//     void allPlayersTest() {
//         List<Player> players = List.of(
//             new Player(), new Player(), new Player(), new Player(), new Player(), new Player(), new Player(), new Player()
//         );
//         MatchingSystem bracket = new TreeBracket();
//         bracket.loadPlayers(players);

//         Set<Player> allPlayers = new HashSet<>();
//         for (int i = 0; i < 4; i++) {
//             allPlayers.addAll(bracket.getCurrentPlayers());
//             bracket.chooseWinner(bracket.getCurrentPlayers().iterator().next());
//             bracket.nextMatch();
//         }

//         Assertions.assertEquals(8, allPlayers.size());
//     }

//     @Test
//     void freeTicketTest() {
//         List<Player> players = List.of(
//             new Player(), new Player(), new Player(), new Player(), new Player(), new Player()
//         );
//         MatchingSystem bracket = new TreeBracket();
//         bracket.loadPlayers(players);
//     }

//     @Test
//     void tornamentWithFreeTicketTest() {
//         List<Player> players = List.of(
//             new Player(), new Player(), new Player(), new Player(), new Player(), new Player()
//         );
//         TreeBracket bracket = new TreeBracket();
//         bracket.loadPlayers(players);
//         for (int i = 0; i < 4; i++) {
//             Set<Player> currPlayers = bracket.getCurrentPlayers();
//             bracket.chooseWinner(currPlayers.iterator().next());
//             bracket.nextMatch();
//         }
//         Set<Player> currPlayers = bracket.getCurrentPlayers();
//         bracket.chooseWinner(currPlayers.iterator().next());
//     }
// }
