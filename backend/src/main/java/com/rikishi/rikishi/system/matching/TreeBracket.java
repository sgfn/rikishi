package com.rikishi.rikishi.system.matching;

import com.rikishi.rikishi.model.Fight;
import com.rikishi.rikishi.model.User;
import com.rikishi.rikishi.model.WeightClass;
import com.rikishi.rikishi.model.entity.Duel;

import java.util.*;

public class TreeBracket implements MatchingSystem, MatchingSystem_II {
    private final List<User> arrayTree = new ArrayList<>(15);
    private int actualMatch;
    private OctetRoundName currentRound;
    private WeightClass weightCategory;

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

    private Set<User> getPlayersByFromDuelID(int duelId) {
        int tmp = actualMatch;
        actualMatch = duelId;
        Set<User> players = getCurrentPlayers();
        actualMatch = tmp;
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

        Map<WeightClass, Integer> weightCategoryFrequency = new HashMap<>();
        for (User user : players) {
            WeightClass userCategory = user.weightClass();
            if (weightCategoryFrequency.containsKey(userCategory)) {
                weightCategoryFrequency.put(
                    userCategory,
                    weightCategoryFrequency.get(userCategory) + 1
                );
            } else {
                weightCategoryFrequency.put(userCategory, 1);
            }
        }
        Optional<Map.Entry<WeightClass, Integer>> maxEntry = weightCategoryFrequency.entrySet()
            .stream()
            .max(Map.Entry.comparingByValue());

        maxEntry.ifPresent(entry -> weightCategory = entry.getKey());
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
        User temp = arrayTree.get(index1);
        arrayTree.set(index1, arrayTree.get(index2));
        arrayTree.set(index2, temp);
    }

    public List<User> getArrayTree() {
        return arrayTree;
    }

    private List<Fight> getDuelsFromRange(int a, int b) {
        List<Fight> duels = new ArrayList<>();
        for (int i = a; i < b; i++) {
            ArrayList<User> players = new ArrayList<>(getPlayersByFromDuelID(i));
            long winnerId = (arrayTree.get(i) == null) ? -1 : arrayTree.get(i).id();
            if (players.size() == 1) {
                winnerId = players.get(0).id();
            }
            while (players.size() < 2) players.add(null);
            long player1_id = players.get(0) != null ? players.get(0).id() : -2;
            long player2_id = players.get(1) != null ? players.get(1).id() : -2;
            int score1 = (winnerId == player1_id) ? 1 : 0;
            int score2 = (winnerId == player2_id) ? 1 : 0;
            duels.add(new Fight(
                i,
                players.get(0),
                players.get(1),
                0,
                score1,
                score2,
                weightCategory,
                winnerId
            ));
        }
        return duels;
    }

    public List<Fight> getAllFights() {
        return getDuelsFromRange(0, 7);
    }

    public List<Fight> getCurrentFights() {
        return getDuelsFromRange(currentRound.getIndexStart(), currentRound.getIndexBound());
    }

    public void updateFight(Fight fight) {
        if (fight.winnerId() == -1) throw new RuntimeException("Fight is not resolved");
        long winnerId = fight.winnerId();
        actualMatch = (int) fight.id();
        User winner = (User) getCurrentPlayers().stream().filter(player -> player.id() == winnerId).toArray()[0];
        chooseWinner(winner);

        for (int id = currentRound.getIndexStart(); id <= currentRound.getIndexBound(); id++) {
            if (arrayTree.get(id) == null) {
                return;
            }
        }
        currentRound = currentRound.goUp();
        actualMatch = currentRound.getIndexStart();
    }
}
