package com.rikishi.rikishi.system.matching;


import com.rikishi.rikishi.model.User;
import com.rikishi.rikishi.model.entity.Duel;

import java.util.*;

public class AllVsAll implements MatchingSystem {
    private final Map<User, Integer> playerToId = new HashMap<>();
    private final Map<User, Integer> playerWins = new HashMap<>();
    private int mostWins = -1;
    private final List<List<List<Integer>>> results = new ArrayList<>();
    private final List<Duel> duels = new ArrayList<>();
    private int currentRow = 0;
    private int currentColumn = 1;
    private int nextPlayerFor0 = 2;
    private int numOfPlayers;
    private boolean matchInProgress = false;
    private long duelIdCounter = 0;

    @Override
    public void loadPlayers(Collection<User> players) {
        numOfPlayers = players.size();
        if (numOfPlayers > 5) {
            throw new IllegalArgumentException("Invalid number of players");
        }
        fillPlayersMap(players);
        resultsListInit(numOfPlayers, numOfPlayers, 2);
        generateDuels(players);
        matchInProgress = true;
    }

    private void fillPlayersMap(Collection<User> players) {
        int id = 0;
        for (User player : players) {
            playerToId.put(player, id);
            playerWins.put(player, 0);
            id++;
        }
    }

    public void resultsListInit(int xSize, int ySize, int zSize) {
//        create numOfPlayers*numOfPlayers*2 matrix
        for (int i = 0; i < xSize; i++) {
            List<List<Integer>> yList = new ArrayList<>();
            for (int j = 0; j < ySize; j++) {
                List<Integer> zList = new ArrayList<>();
                for (int k = 0; k < zSize; k++) {
                    zList.add(0);
                }
                yList.add(zList);
            }
            results.add(yList);
        }
    }

    private void generateDuels(Collection<User> players) {
        int duelNumber = 1;
        int currentRow = 0;
        int currentColumn = 1;
        int nextPlayerFor0 = 2;

        // Iterujemy po macierzy wyników w określonej kolejności
        while (duelNumber <= numOfPlayers * (numOfPlayers - 1) / 2) {
            User player1 = idToPlayer(currentRow);
            User player2 = idToPlayer(currentColumn);

            Duel duel = new Duel(
                player1.id(),
                player1.name(),
                player2.id(),
                player2.name(),
                duelNumber,
                results.get(getPlayerId(player1)).get(getPlayerId(player2)).get(0),
                results.get(getPlayerId(player1)).get(getPlayerId(player2)).get(1),
                duelIdCounter++,
                player1.weightClass().name() + " - " + player1.sex().toString().charAt(0),
                -1
            );
            duels.add(duel);
            duelNumber++;

            // Aktualizujemy indeksy do kolejnego pojedynku w macierzy wyników
            if (currentColumn == numOfPlayers - 1) {
                currentRow = 0;
                currentColumn = nextPlayerFor0;
                nextPlayerFor0++;
            } else {
                currentRow++;
                currentColumn++;
            }
        }
    }

    public Integer getPlayerId(User player) {
        return playerToId.get(player);
    }

    public Integer getPlayerWins(User player) {
        return playerWins.get(player);
    }

    public User idToPlayer(Integer playerId) {
        for (Map.Entry<User, Integer> entry : playerToId.entrySet()) {
            if (entry.getValue().equals(playerId)) {
                return entry.getKey();
            }
        }
        return null;
    }

    @Override
    public void nextMatch() {
//        walking on the diagonal of the matrix
        if (matchInProgress) {
            throw new IllegalStateException("Match is still in progress");
        }
        if (currentRow == 0 && currentColumn == numOfPlayers - 1) {
            throw new RuntimeException("This part of tournament has ended");
        } else if (currentColumn == numOfPlayers - 1) {
            currentRow = 0;
            currentColumn = nextPlayerFor0;
            nextPlayerFor0++;
        } else {
            currentRow++;
            currentColumn++;
        }
        matchInProgress = true;
    }

    public List<Duel> getDuels() {
        return duels;
    }

    @Override
    public void chooseWinner(User winner) {
        int loserId;
        int winnerId;
        if (getPlayerId(winner) == currentColumn) {
            loserId = currentRow;
            winnerId = currentColumn;
        } else {
            loserId = currentColumn;
            winnerId = currentRow;
        }
        int currentValue = results.get(winnerId).get(loserId).get(0);
//        if we play to 2 wins
        if (currentValue == 3 || results.get(winnerId).get(loserId).get(1) == 3) {
            throw new IllegalArgumentException("Match has been settled earlier");
        } else {
            results.get(winnerId).get(loserId).set(0, currentValue + 1);
            results.get(loserId).get(winnerId).set(1, currentValue + 1);
        }
        if (results.get(winnerId).get(loserId).get(0) == 2) {
            matchInProgress = false;
            int newScore = playerWins.getOrDefault(winner, 0) + 1;
            if (newScore > mostWins) {
                mostWins = newScore;
            }
            playerWins.put(winner, newScore);
//            System.out.println("Winner: " + winner);

            // Aktualizacja listy pojedynków
            Duel foundDuel = null;
            for (Duel duel : duels) {
                if ((duel.id1Contestant() == winner.getId() && duel.id2Contestant() == idToPlayer(loserId).getId()) ||
                    (duel.id1Contestant() == idToPlayer(loserId).getId() && duel.id2Contestant() == winner.getId())) {
                    foundDuel = duel;
                    break;
                }
            }
            if (foundDuel != null) {
                duels.remove(foundDuel);
                if (Objects.equals(winner.name(), foundDuel.name1())) {
                    duels.add(new Duel(foundDuel.id1Contestant(), foundDuel.name1(),
                        foundDuel.id2Contestant(), foundDuel.name2(),
                        foundDuel.number(), results.get(winnerId).get(loserId).get(0), results.get(winnerId).get(loserId).get(1),
                        foundDuel.id(), foundDuel.weightCategory(), winner.id()));
                } else if (Objects.equals(winner.name(), foundDuel.name2())) {
                    duels.add(new Duel(foundDuel.id1Contestant(), foundDuel.name1(),
                        foundDuel.id2Contestant(), foundDuel.name2(),
                        foundDuel.number(), results.get(loserId).get(winnerId).get(0), results.get(loserId).get(winnerId).get(1),
                        foundDuel.id(), foundDuel.weightCategory(), winner.id()));
                }
            }
        }
    }

    @Override
    public Set<User> getCurrentPlayers() {
        return Set.of(idToPlayer(currentColumn), idToPlayer(currentRow));
    }

    public int getMostWins() {
        return mostWins;
    }

    public Map<User, Integer> getPlayerWins() {
        return playerWins;
    }

    public List<Integer> getConcreteResult(int row, int column) {
        return results.get(row).get(column);
    }
}
