package com.rikishi.backend.matching.systems;

import com.rikishi.backend.matching.Player;

import java.util.*;

public class TreeBracket implements MatchingSystem {
    private final List<Player> arrayTree = new ArrayList<>(15);
    private int actualMatch;
    private OctetRoundName currentRound;

    @Override
    public void nextMatch() {
        if (arrayTree.get(actualMatch) == null) {
            throw new RuntimeException("No winner of the round");
        }
        if (currentRound == OctetRoundName.FINAL) throw new RuntimeException("There is nothing afet finals");
        if (actualMatch + 1 < currentRound.getIndexBound()) actualMatch++;
        else {
            currentRound = currentRound.goUp();
            actualMatch = currentRound.getIndexStart();
        }
        if (getCurrentPlayers().size() == 1) {
            chooseWinner(getCurrentPlayers().iterator().next());
            nextMatch();
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
        players.add(arrayTree.get((actualMatch + 1) * 2 - 1));
        players.add(arrayTree.get((actualMatch + 1) * 2));
        players.remove(null);
        return players;
    }

    @Override
    public void loadPlayers(Collection<Player> players) {
        List<Player> playersArray = new ArrayList<>(players);
        Collections.shuffle(playersArray);
        currentRound = OctetRoundName.FIRST_FIGHT;
        actualMatch = OctetRoundName.FIRST_FIGHT.getIndexStart();
        if (players.size() == 8) {
            for (int i = 0; i < players.size() * 2 - 1; i++) {
                arrayTree.add(null);
            }
            Iterator<Player> it = playersArray.iterator();
            for (int i = 7; i < 15 && it.hasNext(); i++) {
                arrayTree.set(i, it.next()); // Assign the next player
            }
        } else if (5 < players.size() && players.size() < 8) {
            for (int i = 0; i < 15; i++) {
                arrayTree.add(null);
            }
            for (int i = 0, k = 0; k < players.size(); i = ((i + 4) % 7), k++) {
                arrayTree.set(7 + i, playersArray.get(k));
            }
        } else {
            throw new RuntimeException("implement only for 6 to 8 players");
        }
    }

    public void printBracket() {
        if (arrayTree.isEmpty()) {
            System.out.println("Empty Bracket.");
            return;
        }
        printSubTree(arrayTree, 0, 0);
    }

    private static void printSubTree(List<Player> treeList, int index, int level) {
        if (index >= treeList.size()) {
            return;
        }
        printSubTree(treeList, 2 * index + 2, level + 1);

        printCurrentNode(treeList.get(index), level);

        printSubTree(treeList, 2 * index + 1, level + 1);
    }

    private static void printCurrentNode(Object value, int level) {
        for (int i = 0; i < level; i++) {
            System.out.print("\t\t");
        }
        System.out.println(value);
    }
}
