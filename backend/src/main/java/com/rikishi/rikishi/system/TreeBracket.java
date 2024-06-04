package com.rikishi.rikishi.system;

import com.rikishi.rikishi.model.User;

import java.util.*;

public class TreeBracket implements MatchingSystem {
    private final List<User> arrayTree = new ArrayList<>(15);
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
    public void chooseWinner(User winner) {
        if (getCurrentPlayers().contains(winner)) arrayTree.set(actualMatch, winner);
        else throw new IllegalArgumentException("choosed winner do not play this match");
    }

    @Override
    public Set<User> getCurrentPlayers() {
        Set<User> players = new HashSet<>();
        if (arrayTree.isEmpty()) return players;
        if (arrayTree.size() <= (actualMatch + 1) * 2) throw new RuntimeException("Players are out of bound");
        players.add(arrayTree.get((actualMatch + 1) * 2 - 1));
        players.add(arrayTree.get((actualMatch + 1) * 2));
        players.remove(null);
        return players;
    }

    @Override
    public void loadPlayers(Collection<User> players) {
        List<User> playersArray = new ArrayList<>(players);
        Collections.shuffle(playersArray);
        currentRound = OctetRoundName.FIRST_FIGHT;
        actualMatch = OctetRoundName.FIRST_FIGHT.getIndexStart();
        if (players.size() == 8) {
            for (int i = 0; i < players.size() * 2 - 1; i++) {
                arrayTree.add(null);
            }
            Iterator<User> it = playersArray.iterator();
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

    private static void printSubTree(List<User> treeList, int index, int level) {
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

    public void swapPlayers(int index1, int index2) {
        if (index1 < 0 || index1 >= arrayTree.size() || index2 < 0 || index2 >= arrayTree.size()) {
            throw new IllegalArgumentException("Index out of bounds");
        }
        index1 = arrayTree.size() - index1 - 1;
        index2 = arrayTree.size() - index2 - 1;
        if (index1 < 7 || index2 < 7) {
            throw new IllegalArgumentException("This part is reserved for next matches. Wrong index.");
        }
        if (arrayTree.get(index1) == null || arrayTree.get(index2) == null) {
            throw new IllegalArgumentException("Cannot swap with null");
        }
        Player temp = arrayTree.get(index1);
        arrayTree.set(index1, arrayTree.get(index2));
        arrayTree.set(index2, temp);
    }

    public List<Player> getArrayTree() {
        return arrayTree;
    }
}
