package backend.matching.systems;

import backend.matching.Player;

import java.util.*;

public class AllVsAll implements MatchingSystem {
    private final Map<Player, Integer> playerToId = new HashMap<>();
    private final Map<Player, Integer> playerWins = new HashMap<>();
    private final List<List<List<Integer>>> results = new ArrayList<>();
    private int currentRow = 0;
    private int currentColumn = 1;
    private int nextPlayerFor0 = 2;
    private int numOfPlayers;
    private boolean matchInProgress = false;

    @Override
    public void loadPlayers(Collection<Player> players) {
        numOfPlayers = players.size();
        if (numOfPlayers > 5 || numOfPlayers < 3) {
            throw new IllegalArgumentException("Invalid number of players");
        }
        fillPlayersMap(players);
        resultsListInit(numOfPlayers, numOfPlayers, 2);
    }

    private void fillPlayersMap(Collection<Player> players) {
        int id = 0;
        for (Player player : players) {
            playerToId.put(player, id);
            playerWins.put(player, 0);
            id++;
        }
    }

    public void resultsListInit(int xSize, int ySize, int zSize) {
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

    public Integer getPlayerId(Player player) {
        return playerToId.get(player);
    }

    public Integer getPlayerWins(Player player) {
        return playerWins.get(player);
    }

    public Player idToPlayer(Integer playerId) {
        for (Map.Entry<Player, Integer> entry : playerToId.entrySet()) {
            if (entry.getValue().equals(playerId)) {
                return entry.getKey();
            }
        }
        return null;
    }

    @Override
    public void nextMatch() {
        if (matchInProgress) {
            throw new IllegalStateException("Match is still in progress");
        }
        matchInProgress = true;
        if (currentRow == numOfPlayers - 1 && currentColumn == numOfPlayers - 1) {
            throw new RuntimeException("This part of tournament has ended");
        } else if (currentColumn == numOfPlayers - 1) {
            currentRow = 0;
            currentColumn = nextPlayerFor0;
            nextPlayerFor0++;
        } else {
            currentRow++;
            currentColumn++;
        }
    }

    @Override
    public void chooseWinner(Player winner) {
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
        if (currentValue == 2 || results.get(winnerId).get(loserId).get(1) == 2) {
            throw new IllegalArgumentException("Match has been settled earlier");
        }
        results.get(winnerId).get(loserId).set(0, currentValue + 1);
        results.get(loserId).get(winnerId).set(1, currentValue + 1);
        if (results.get(winnerId).get(loserId).get(0) == 2) {
            matchInProgress = false;
            playerWins.put(winner, playerWins.getOrDefault(winner, 0) + 1);
        }
    }

    @Override
    public Set<Player> getCurrentPlayers() {
        return Set.of(idToPlayer(currentColumn), idToPlayer(currentRow));
    }

}
