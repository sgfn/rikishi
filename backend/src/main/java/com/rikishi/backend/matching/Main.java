package com.rikishi.backend.matching;

import com.rikishi.backend.matching.systems.TreeBracket;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Player> players = List.of(new Player(), new Player(), new Player(), new Player(), new Player(), new Player());
        TreeBracket bracket = new TreeBracket();
        bracket.loadPlayers(players);
        bracket.printBracket();
    }
}
